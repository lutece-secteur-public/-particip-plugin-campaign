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

/**
 * This class provides Data Access methods for Phase objects
 */
public final class PhaseDAO implements IPhaseDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_phase, id_campaign, starting_date, ending_date, label, order_num FROM campaign_phase WHERE id_phase = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO campaign_phase ( id_campaign, starting_date, ending_date, label, order_num ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM campaign_phase WHERE id_phase = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE campaign_phase SET id_phase = ?, id_campaign = ?, starting_date = ?, ending_date = ?, label = ?, order_num = ? WHERE id_phase = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_phase, id_campaign, starting_date, ending_date, label, order_num FROM campaign_phase";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_phase FROM campaign_phase";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Phase phase, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++, phase.getIdCampaign( ) );
            daoUtil.setTimestamp( nIndex++, phase.getStartingTimeStampDate( ) );
            daoUtil.setTimestamp( nIndex++, phase.getEndingTimeStampDate( ) );
            daoUtil.setString( nIndex++, phase.getLabel( ) );
            daoUtil.setInt( nIndex++, phase.getOrderNum( ) );

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
                phase.setIdCampaign( daoUtil.getInt( nIndex++ ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setLabel( daoUtil.getString( nIndex++ ) );
                phase.setOrderNum( daoUtil.getInt( nIndex ) );
            }

            daoUtil.free( );
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
            daoUtil.free( );
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
            daoUtil.setInt( nIndex++, phase.getIdCampaign( ) );
            daoUtil.setTimestamp( nIndex++, phase.getStartingTimeStampDate( ) );
            daoUtil.setTimestamp( nIndex++, phase.getEndingTimeStampDate( ) );
            daoUtil.setString( nIndex++, phase.getLabel( ) );
            daoUtil.setInt( nIndex++, phase.getOrderNum( ) );
            daoUtil.setInt( nIndex, phase.getId( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
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
                phase.setIdCampaign( daoUtil.getInt( nIndex++ ) );
                phase.setStartingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setEndingTimeStampDate( daoUtil.getTimestamp( nIndex++ ) );
                phase.setLabel( daoUtil.getString( nIndex++ ) );
                phase.setOrderNum( daoUtil.getInt( nIndex ) );

                phaseList.add( phase );
            }

            daoUtil.free( );
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

            daoUtil.free( );
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

            daoUtil.free( );
            return phaseList;
        }
    }
}
