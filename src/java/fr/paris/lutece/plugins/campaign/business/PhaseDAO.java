/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.campaign.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * This class provides Data Access methods for Phase objects
 */
public final class PhaseDAO implements IPhaseDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_phase, campaign_code, starting_date, ending_date, label, order_num, phase_type_code FROM campaign_phase WHERE id_phase = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO campaign_phase ( campaign_code, starting_date, ending_date, label, order_num, phase_type_code ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM campaign_phase WHERE id_phase = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE campaign_phase SET id_phase = ?, campaign_code = ?, starting_date = ?, ending_date = ?, label = ?, order_num = ?, phase_type_code = ? WHERE id_phase = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_phase, campaign_code, starting_date, ending_date, label, order_num, phase_type_code FROM campaign_phase";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_phase FROM campaign_phase";
    private static final String SQL_QUERY_SELECT_PHASE_TYPE = "SELECT phase_type_code, label FROM campaign_phase_types";
    private static final String SQL_QUERY_SELECT_BY_CAMPAIGN_AND_CODE_PHASE = "SELECT id_phase, campaign_code, starting_date, ending_date, label, order_num, phase_type_code FROM campaign_phase WHERE campaign_code = ? and phase_type_code = ? ";
    private static final String SQL_QUERY_SELECTALL_BY_CAMPAIGN = SQL_QUERY_SELECTALL + " WHERE campaign_code = ?";



    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Phase phase, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, phase.getCampaignCode( ) );
            daoUtil.setTimestamp( nIndex++, phase.getStartingTimeStampDate( ) );
            daoUtil.setTimestamp( nIndex++, phase.getEndingTimeStampDate( ) );
            daoUtil.setString( nIndex++, phase.getLabel( ) );
            daoUtil.setInt( nIndex++, phase.getOrderNum( ) );
            daoUtil.setString( nIndex++, phase.getCodePhaseType( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                phase.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Phase load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            Phase phase = null;

            if ( daoUtil.next( ) )
            {
                phase = new Phase( );
                int nIndex = 1;

                phase.setId( daoUtil.getInt( nIndex++ ) );
                phase.setCampaignCode( daoUtil.getString( nIndex++ ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setLabel( daoUtil.getString( nIndex++ ) );
                phase.setOrderNum( daoUtil.getInt( nIndex++ ) );
                phase.setCodePhaseType( daoUtil.getString( nIndex ) );

            }

            return phase;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Phase findByCampaignCodeAndPhaseTypeCode( String campaignCode, String phaseTypeCode, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CAMPAIGN_AND_CODE_PHASE, plugin ) )
        {
            daoUtil.setString( 1, campaignCode );
            daoUtil.setString( 2, phaseTypeCode );
            daoUtil.executeQuery( );
            Phase phase = null;

            if ( daoUtil.next( ) )
            {
                phase = new Phase( );
                int nIndex = 1;

                phase.setId( daoUtil.getInt( nIndex++ ) );
                phase.setCampaignCode( daoUtil.getString( nIndex++ ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setLabel( daoUtil.getString( nIndex++ ) );
                phase.setOrderNum( daoUtil.getInt( nIndex++ ) );
                phase.setCodePhaseType( daoUtil.getString( nIndex ) );
            }

            return phase;
        }
    }
    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( Phase phase, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, phase.getId( ) );
            daoUtil.setString( nIndex++, phase.getCampaignCode( ) );
            daoUtil.setTimestamp( nIndex++, phase.getStartingTimeStampDate( ) );
            daoUtil.setTimestamp( nIndex++, phase.getEndingTimeStampDate( ) );
            daoUtil.setString( nIndex++, phase.getLabel( ) );
            daoUtil.setInt( nIndex++, phase.getOrderNum( ) );
            daoUtil.setString( nIndex++, phase.getCodePhaseType( ) );

            daoUtil.setInt( nIndex, phase.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Phase> selectPhasesList( Plugin plugin )
    {
        List<Phase> phaseList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Phase phase = new Phase( );
                int nIndex = 1;

                phase.setId( daoUtil.getInt( nIndex++ ) );
                phase.setCampaignCode( daoUtil.getString( nIndex++ ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setLabel( daoUtil.getString( nIndex++ ) );
                phase.setOrderNum( daoUtil.getInt( nIndex++ ) );
                phase.setCodePhaseType( daoUtil.getString( nIndex ) );
                phaseList.add( phase );
            }

            return phaseList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdPhasesList( Plugin plugin )
    {
        List<Integer> phaseList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                phaseList.add( daoUtil.getInt( 1 ) );
            }

            return phaseList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectPhasesReferenceList( Plugin plugin )
    {
        ReferenceList phaseList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                phaseList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            return phaseList;
        }
    }
    /**
     * {@inheritDoc }
     */
   @Override
    public ReferenceList selectPhasesTypeReferenceList( Plugin plugin )
    {
        ReferenceList phaseList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PHASE_TYPE, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                phaseList.addItem( daoUtil.getString( 1 ), daoUtil.getString( 2 ) );
            }

            return phaseList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<Phase> selectPhasesListByCampaign( String campaignCode, Plugin plugin )
    {
        Collection<Phase> phaseList = new ArrayList< >( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_CAMPAIGN, plugin ) )
        {
        	daoUtil.setString( 1, campaignCode );
        	daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Phase phase = new Phase( );

                phase.setId( daoUtil.getInt( 1 ) );
                phase.setLabel( daoUtil.getString( 2 ) );
                phase.setCampaignCode( daoUtil.getString( 3 ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( 4 ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( 5 ) );

                phaseList.add( phase );
            }
            return phaseList;
        }
        
    }
}
