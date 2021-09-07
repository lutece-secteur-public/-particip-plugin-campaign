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
 * This class provides Data Access methods for Campaign objects
 */
public final class CampaignDAO implements ICampaignDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_campaign, campaign_code, title, description, active FROM campaign_campaign WHERE id_campaign = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO campaign_campaign ( campaign_code, title, description, active ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM campaign_campaign WHERE id_campaign = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE campaign_campaign SET id_campaign = ?, campaign_code = ?, title = ?, description = ?, active = ? WHERE id_campaign = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_campaign, campaign_code, title, description, active FROM campaign_campaign";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_campaign FROM campaign_campaign";
    private static final String SQL_QUERY_SELECTALL_REF = "SELECT campaign_code, title FROM campaign_campaign";
    private static final String SQL_QUERY_SELECT_BY_CODE = SQL_QUERY_SELECTALL + " WHERE campaign_code = ? ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Campaign campaign, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, campaign.getCampaignCode( ) );
            daoUtil.setString( nIndex++, campaign.getTitle( ) );
            daoUtil.setString( nIndex++, campaign.getDescription( ) );
            daoUtil.setBoolean( nIndex++, campaign.getActive( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                campaign.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Campaign load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            Campaign campaign = null;

            if ( daoUtil.next( ) )
            {
                campaign = new Campaign( );
                int nIndex = 1;

                campaign.setId( daoUtil.getInt( nIndex++ ) );
                campaign.setCampaignCode( daoUtil.getString( nIndex++ ) );
                campaign.setTitle( daoUtil.getString( nIndex++ ) );
                campaign.setDescription( daoUtil.getString( nIndex++ ) );
                campaign.setActive( daoUtil.getBoolean( nIndex ) );
            }

            return campaign;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Campaign loadByCampaignCode( String campaignCode, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE, plugin ) )
        {
            daoUtil.setString( 1, campaignCode );
            daoUtil.executeQuery( );
            Campaign campaign = null;

            if ( daoUtil.next( ) )
            {
                campaign = new Campaign( );
                int nIndex = 1;

                campaign.setId( daoUtil.getInt( nIndex++ ) );
                campaign.setCampaignCode( daoUtil.getString( nIndex++ ) );
                campaign.setTitle( daoUtil.getString( nIndex++ ) );
                campaign.setDescription( daoUtil.getString( nIndex++ ) );
                campaign.setActive( daoUtil.getBoolean( nIndex ) );
            }

            return campaign;
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
    public void store( Campaign campaign, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, campaign.getId( ) );
            daoUtil.setString( nIndex++, campaign.getCampaignCode( ) );
            daoUtil.setString( nIndex++, campaign.getTitle( ) );
            daoUtil.setString( nIndex++, campaign.getDescription( ) );
            daoUtil.setBoolean( nIndex++, campaign.getActive( ) );
            daoUtil.setInt( nIndex, campaign.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Campaign> selectCampaignsList( Plugin plugin )
    {
        List<Campaign> campaignList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Campaign campaign = new Campaign( );
                int nIndex = 1;

                campaign.setId( daoUtil.getInt( nIndex++ ) );
                campaign.setCampaignCode( daoUtil.getString( nIndex++ ) );
                campaign.setTitle( daoUtil.getString( nIndex++ ) );
                campaign.setDescription( daoUtil.getString( nIndex++ ) );
                campaign.setActive( daoUtil.getBoolean( nIndex ) );

                campaignList.add( campaign );
            }

            return campaignList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdCampaignsList( Plugin plugin )
    {
        List<Integer> campaignList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                campaignList.add( daoUtil.getInt( 1 ) );
            }

            return campaignList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectCampaignsReferenceList( Plugin plugin )
    {
        ReferenceList campaignList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_REF, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                campaignList.addItem( daoUtil.getString( 1 ), daoUtil.getString( 2 ) );
            }

            return campaignList;
        }
    }

}
