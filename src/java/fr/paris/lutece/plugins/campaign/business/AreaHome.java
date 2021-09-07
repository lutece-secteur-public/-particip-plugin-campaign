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

/**
 * This class provides instances management methods (create, find, ...) for Area objects
 */
public final class AreaHome
{
    // Static variable pointed at the DAO instance
    private static IAreaDAO _dao = SpringContextService.getBean( "campaign.areaDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "campaign" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AreaHome( )
    {
    }

    /**
     * Create an instance of the area class
     * 
     * @param area
     *            The instance of the Area which contains the informations to store
     * @return The instance of area which has been created with its primary key.
     */
    public static Area create( Area area )
    {
        _dao.insert( area, _plugin );

        return area;
    }

    /**
     * Update of the area which is specified in parameter
     * 
     * @param area
     *            The instance of the Area which contains the data to store
     * @return The instance of the area which has been updated
     */
    public static Area update( Area area )
    {
        _dao.store( area, _plugin );

        return area;
    }

    /**
     * Remove the area whose identifier is specified in parameter
     * 
     * @param nKey
     *            The area Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a area whose identifier is specified in parameter
     * 
     * @param nKey
     *            The area primary key
     * @return an instance of Area
     */
    public static Area findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the area objects and returns them as a list
     * 
     * @return the list which contains the data of all the area objects
     */
    public static List<Area> getAreasList( )
    {
        return _dao.selectAreasList( _plugin );
    }

    /**
     * Load the id of all the area objects and returns them as a list
     * 
     * @return the list which contains the id of all the area objects
     */
    public static List<Integer> getIdAreasList( )
    {
        return _dao.selectIdAreasList( _plugin );
    }

    /**
     * Load the data of the area objects by campaign code and returns them as a referenceList
     * 
     * @param campaignCode
     *            the campaign code
     * @return the referenceList which contains the data of all the area objects
     */
    public static ReferenceList getAreasReferenceList( String campaignCode )
    {
        return _dao.selectAreasReferenceList( campaignCode, _plugin );
    }
}
