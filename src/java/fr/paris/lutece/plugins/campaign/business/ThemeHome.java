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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for Theme objects
 */
public final class ThemeHome
{
    // Static variable pointed at the DAO instance
    private static IThemeDAO _dao = SpringContextService.getBean( "campaign.themeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "campaign" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ThemeHome( )
    {
    }

    /**
     * Create an instance of the theme class
     * 
     * @param theme
     *            The instance of the Theme which contains the informations to store
     * @return The instance of theme which has been created with its primary key.
     */
    public static Theme create( Theme theme )
    {
        _dao.insert( theme, _plugin );

        return theme;
    }

    /**
     * Update of the theme which is specified in parameter
     * 
     * @param theme
     *            The instance of the Theme which contains the data to store
     * @return The instance of the theme which has been updated
     */
    public static Theme update( Theme theme )
    {
        _dao.store( theme, _plugin );

        return theme;
    }

    /**
     * Remove the theme whose identifier is specified in parameter
     * 
     * @param nKey
     *            The theme Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a theme whose identifier is specified in parameter
     * 
     * @param nKey
     *            The theme primary key
     * @return an instance of Theme
     */
    public static Theme findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the theme objects and returns them as a list
     * 
     * @return the list which contains the data of all the theme objects
     */
    public static List<Theme> getThemesList( )
    {
        return _dao.selectThemesList( _plugin );
    }

    /**
     * Load the id of all the theme objects and returns them as a list
     * 
     * @return the list which contains the id of all the theme objects
     */
    public static List<Integer> getIdThemesList( )
    {
        return _dao.selectIdThemesList( _plugin );
    }

    /**
     * Load the data of all the theme objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the theme objects
     */
    public static ReferenceList getThemesReferenceList( )
    {
        return _dao.selectThemesReferenceList( _plugin );
    }

    /**
     * Load the data of all the campagneTheme objects for a campagne and returns them in form of a collection
     * 
     * @param codeCampagne
     *            Campagne courante
     * 
     * @return the collection which contains the data of all the campagneTheme objects
     */
    public static Collection<Theme> getThemesListByCampaign( String codeCampaign )
    {
        return _dao.selectThemesListByCampaign( codeCampaign, _plugin );
    }

    /**
     * Load the data of all the campagneTheme objects mapped from campagne code and returns them in form of a map
     * 
     * @return the collection which contains the data of all the campagneTheme objects
     */
    public static Map<String, List<Theme>> getThemesMapByCampaign( )
    {
        return _dao.selectThemesMapByCampaign( _plugin );
    }

    /**
     * Returns an instance of a campagneTheme whose identifier is specified in parameter
     * 
     * @param codeTheme
     *            The codeTheme
     * @return an instance of CampagneTheme
     */
    public static Theme findByCodeTheme( String codeTheme )
    {
        return _dao.loadByCodeTheme( codeTheme, _plugin );
    }

}
