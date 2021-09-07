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
 * This class provides Data Access methods for Area objects
 */
public final class AreaDAO implements IAreaDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_area, area_code, campaign_code, title, type, number_votes, active FROM campaign_area WHERE id_area = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO campaign_area ( area_code, campaign_code, title, type, number_votes, active ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM campaign_area WHERE id_area = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE campaign_area SET id_area = ?, area_code= ?, campaign_code = ?, title = ?, type = ?, number_votes = ?, active = ? WHERE id_area = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_area, area_code, campaign_code, title, type, number_votes, active FROM campaign_area";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_area FROM campaign_area";
    private static final String SQL_QUERY_SELECT_REF_BY_CAMPAIGN = "SELECT area_code, title FROM campaign_area where campaign_code = ? ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Area area, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, area.getAreaCode( ) );
            daoUtil.setString( nIndex++, area.getCampaignCode( ) );
            daoUtil.setString( nIndex++, area.getTitle( ) );
            daoUtil.setString( nIndex++, area.getType( ) );
            daoUtil.setInt( nIndex++, area.getNumberVotes( ) );
            daoUtil.setBoolean( nIndex++, area.getActive( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                area.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Area load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            Area area = null;

            if ( daoUtil.next( ) )
            {
                area = new Area( );
                int nIndex = 1;

                area.setId( daoUtil.getInt( nIndex++ ) );
                area.setAreaCode( daoUtil.getString( nIndex++ ) );
                area.setCampaignCode( daoUtil.getString( nIndex++ ) );
                area.setTitle( daoUtil.getString( nIndex++ ) );
                area.setType( daoUtil.getString( nIndex++ ) );
                area.setNumberVotes( daoUtil.getInt( nIndex++ ) );
                area.setActive( daoUtil.getBoolean( nIndex ) );
            }

            return area;
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
    public void store( Area area, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, area.getId( ) );
            daoUtil.setString( nIndex++, area.getAreaCode( ) );
            daoUtil.setString( nIndex++, area.getCampaignCode( ) );
            daoUtil.setString( nIndex++, area.getTitle( ) );
            daoUtil.setString( nIndex++, area.getType( ) );
            daoUtil.setInt( nIndex++, area.getNumberVotes( ) );
            daoUtil.setBoolean( nIndex++, area.getActive( ) );
            daoUtil.setInt( nIndex, area.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Area> selectAreasList( Plugin plugin )
    {
        List<Area> areaList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Area area = new Area( );
                int nIndex = 1;

                area.setId( daoUtil.getInt( nIndex++ ) );
                area.setAreaCode( daoUtil.getString( nIndex++ ) );
                area.setCampaignCode( daoUtil.getString( nIndex++ ) );
                area.setTitle( daoUtil.getString( nIndex++ ) );
                area.setType( daoUtil.getString( nIndex++ ) );
                area.setNumberVotes( daoUtil.getInt( nIndex++ ) );
                area.setActive( daoUtil.getBoolean( nIndex ) );

                areaList.add( area );
            }

            return areaList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAreasList( Plugin plugin )
    {
        List<Integer> areaList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                areaList.add( daoUtil.getInt( 1 ) );
            }

            return areaList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAreasReferenceList( String campaignCode, Plugin plugin )
    {
        ReferenceList areaList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_REF_BY_CAMPAIGN, plugin ) )
        {
            daoUtil.setString( 1, campaignCode );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                areaList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }
            return areaList;
        }
    }
}
