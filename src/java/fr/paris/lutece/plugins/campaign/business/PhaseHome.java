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
import java.util.Collection;

/**
 * This class provides instances management methods (create, find, ...) for Phase objects
 */
public final class PhaseHome
{
    // Static variable pointed at the DAO instance
    private static IPhaseDAO _dao = SpringContextService.getBean( "campaign.phaseDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "campaign" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PhaseHome( )
    {
    }

    /**
     * Create an instance of the phase class
     * 
     * @param phase
     *            The instance of the Phase which contains the informations to store
     * @return The instance of phase which has been created with its primary key.
     */
    public static Phase create( Phase phase )
    {
        _dao.insert( phase, _plugin );

        return phase;
    }

    /**
     * Update of the phase which is specified in parameter
     * 
     * @param phase
     *            The instance of the Phase which contains the data to store
     * @return The instance of the phase which has been updated
     */
    public static Phase update( Phase phase )
    {
        _dao.store( phase, _plugin );

        return phase;
    }

    /**
     * Remove the phase whose identifier is specified in parameter
     * 
     * @param nKey
     *            The phase Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a phase whose identifier is specified in parameter
     * 
     * @param nKey
     *            The phase primary key
     * @return an instance of Phase
     */
    public static Phase findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the phase objects and returns them as a list
     * 
     * @return the list which contains the data of all the phase objects
     */
    public static List<Phase> getPhasesList( )
    {
        return _dao.selectPhasesList( _plugin );
    }

    /**
     * Load the id of all the phase objects and returns them as a list
     * 
     * @return the list which contains the id of all the phase objects
     */
    public static List<Integer> getIdPhasesList( )
    {
        return _dao.selectIdPhasesList( _plugin );
    }

    /**
     * Load the data of all the phase objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the phase objects
     */
    public static ReferenceList getPhasesReferenceList( )
    {
        return _dao.selectPhasesReferenceList( _plugin );
    }

    /**
     * Load the data of all the phase type objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the phase objects
     */
    public static ReferenceList getPhasesTypeReferenceList( )
    {
        return _dao.selectPhasesTypeReferenceList( _plugin );
    }

    /**
     * 
     * @param campaignCode
     *            the campaign code
     * @param phaseTypeCode
     *            the phase type code
     * @param plugin
     *            the plugin
     * @return The instance of the phase
     */
    public static Phase findByCampaignCodeAndPhaseTypeCode( String campaignCode, String phaseTypeCode )
    {

        return _dao.findByCampaignCodeAndPhaseTypeCode( campaignCode, phaseTypeCode, _plugin );
    }

    /**
     * Load the data of all the campagnePhase objects for a campagne and returns them in form of a collection
     * 
     * @param campagneCode
     *            the campagne code
     * @return the collection which contains the data of all the campagnePhase objects
     */
    public static Collection<Phase> getPhasesListByCampaign( String campagneCode )
    {
        return _dao.selectPhasesListByCampaign( campagneCode, _plugin );
    }
}
