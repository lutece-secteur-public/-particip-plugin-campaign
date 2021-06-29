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
import fr.paris.lutece.portal.business.file.FileHome;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class provides Data Access methods for Theme objects
 */
public final class ThemeDAO implements IThemeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_theme, campaign_code, code_theme, title, description, active, front_rgb, image_file FROM campaign_theme WHERE id_theme = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO campaign_theme ( campaign_code, code_theme, title, description, active, front_rgb, image_file ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM campaign_theme WHERE id_theme = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE campaign_theme SET id_theme = ?, campaign_code = ?, code_theme = ?, title = ?, description = ?, active = ?, front_rgb = ?, image_file = ? WHERE id_theme = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_theme, campaign_code, code_theme, title, description, active, front_rgb, image_file FROM campaign_theme";
    private static final String SQL_QUERY_SELECTALL_BY_CAMPAGNE = SQL_QUERY_SELECTALL + " WHERE code_campagne = ?";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_theme FROM campaign_theme";
    private static final String SQL_QUERY_SELECT_BY_TITLETHEME = "SELECT id_theme, campaign_code, code_theme, title, description, active, front_rgb, image_file FROM campaign_theme WHERE code_theme = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Theme theme, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, theme.getCampaignCode( ) );
            daoUtil.setString( nIndex++, theme.getTitle( ) );
            daoUtil.setString( nIndex++, theme.getDescription( ) );
            daoUtil.setBoolean( nIndex++, theme.getActive( ) );
            daoUtil.setString( nIndex++, theme.getFrontRgb( ) );
            daoUtil.setInt( nIndex++, theme.getImageFile( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                theme.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Theme load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            Theme theme = null;

            if ( daoUtil.next( ) )
            {
                theme = new Theme( );
                int nIndex = 1;

                theme.setId( daoUtil.getInt( nIndex++ ) );
                theme.setCampaignCode( daoUtil.getString( nIndex++ ) );
                theme.setTitle( daoUtil.getString( nIndex++ ) );
                theme.setDescription( daoUtil.getString( nIndex++ ) );
                theme.setActive( daoUtil.getBoolean( nIndex++ ) );
                theme.setFrontRgb( daoUtil.getString( nIndex++ ) );
                theme.setImageFile( daoUtil.getInt( nIndex ) );
            }

            daoUtil.free( );
            return theme;
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
    public void store( Theme theme, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, theme.getId( ) );
            daoUtil.setString( nIndex++, theme.getCampaignCode( ) );
            daoUtil.setString( nIndex++, theme.getTitle( ) );
            daoUtil.setString( nIndex++, theme.getDescription( ) );
            daoUtil.setBoolean( nIndex++, theme.getActive( ) );
            daoUtil.setString( nIndex++, theme.getFrontRgb( ) );
            daoUtil.setInt( nIndex++, theme.getImageFile( ) );
            daoUtil.setInt( nIndex, theme.getId( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Theme> selectThemesList( Plugin plugin )
    {
        List<Theme> themeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                Theme theme = new Theme( );
                int nIndex = 1;

                theme.setId( daoUtil.getInt( nIndex++ ) );
                theme.setCampaignCode( daoUtil.getString( nIndex++ ) );
                theme.setTitle( daoUtil.getString( nIndex++ ) );
                theme.setDescription( daoUtil.getString( nIndex++ ) );
                theme.setActive( daoUtil.getBoolean( nIndex++ ) );
                theme.setFrontRgb( daoUtil.getString( nIndex++ ) );
                theme.setImageFile( daoUtil.getInt( nIndex ) );

                themeList.add( theme );
            }

            daoUtil.free( );
            return themeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdThemesList( Plugin plugin )
    {
        List<Integer> themeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                themeList.add( daoUtil.getInt( 1 ) );
            }

            daoUtil.free( );
            return themeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectThemesReferenceList( Plugin plugin )
    {
        ReferenceList themeList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                themeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            daoUtil.free( );
            return themeList;
        }
    }
    
    private Theme getRow( DAOUtil daoUtil )
    {
        int nCpt = 1;
        Theme Theme = new Theme( );

        Theme.setId( daoUtil.getInt( nCpt++ ) );
        Theme.setCampaignCode( daoUtil.getString( nCpt++ ) );
        Theme.setCode( daoUtil.getString( nCpt++ ) );
        Theme.setTitle( daoUtil.getString( nCpt++ ) );
        Theme.setDescription( daoUtil.getString( nCpt++ ) );
        Theme.setActive( daoUtil.getBoolean( nCpt++ ) );
        Theme.setFrontRgb( daoUtil.getString( nCpt++ ) );
        Theme.setImageFile(daoUtil.getInt( nCpt++ ));
        return Theme;
    }

    
    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<Theme> selectThemesListByCampaign( String campaignCode, Plugin plugin )
    {
        Collection<Theme> ThemeList = new ArrayList<Theme>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_CAMPAGNE, plugin );
        daoUtil.setString( 1, campaignCode );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Theme Theme = getRow( daoUtil );

            ThemeList.add( Theme );
        }

        daoUtil.free( );
        return ThemeList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, List<Theme>> selectThemesMapByCampaign( Plugin plugin )
    {
        Map<String, List<Theme>> ThemeMap = new HashMap<String, List<Theme>>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Theme Theme = getRow( daoUtil );

            List<Theme> ThemeList = ThemeMap.get( Theme.getCampaignCode() );
            if ( ThemeList == null )
            {
                ThemeList = new ArrayList<Theme>( );
                ThemeMap.put( Theme.getCampaignCode(), ThemeList );
            }
            ThemeList.add( Theme );
        }

        daoUtil.free( );
        return ThemeMap;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Theme loadByCodeTheme( String codeTheme, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_TITLETHEME, plugin );
        daoUtil.setString( 1, codeTheme );
        daoUtil.executeQuery( );

        Theme Theme = null;

        if ( daoUtil.next( ) )
        {
        	Theme = getRow( daoUtil );
        }

        daoUtil.free( );
        return Theme;
    }
}
