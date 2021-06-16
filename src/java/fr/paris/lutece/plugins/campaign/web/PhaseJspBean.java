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

package fr.paris.lutece.plugins.campaign.web;

import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.campaign.business.CampaignHome;
import fr.paris.lutece.plugins.campaign.business.Phase;
import fr.paris.lutece.plugins.campaign.business.PhaseHome;
import fr.paris.lutece.plugins.campaign.service.Utils;

/**
 * This class provides the user interface to manage Phase features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManagePhases.jsp", controllerPath = "jsp/admin/plugins/campaign/", right = "CAMPAIGN_MANAGEMENT" )
public class PhaseJspBean extends AbstractManageCampaignsJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_PHASES = "/admin/plugins/campaign/manage_phases.html";
    private static final String TEMPLATE_CREATE_PHASE = "/admin/plugins/campaign/create_phase.html";
    private static final String TEMPLATE_MODIFY_PHASE = "/admin/plugins/campaign/modify_phase.html";

    // Parameters
    private static final String PARAMETER_ID_PHASE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_PHASES = "campaign.manage_phases.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_PHASE = "campaign.modify_phase.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_PHASE = "campaign.create_phase.pageTitle";

    // Markers
    private static final String MARK_PHASE_LIST = "phase_list";
    private static final String MARK_CAMPAIGN_LIST = "campaign_list";
    private static final String MARK_PHASE_TYPE_LIST = "phase_type_list";

    private static final String MARK_PHASE = "phase";

    private static final String PARAMETER_STARTING_DATE_TIME = "start_date_time";
    private static final String PARAMETER_ENDING_DATE_TIME = "end_date_time";

    private static final String JSP_MANAGE_PHASES = "jsp/admin/plugins/campaign/ManagePhases.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_PHASE = "campaign.message.confirmRemovePhase";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "campaign.model.entity.phase.attribute.";

    // Views
    private static final String VIEW_MANAGE_PHASES = "managePhases";
    private static final String VIEW_CREATE_PHASE = "createPhase";
    private static final String VIEW_MODIFY_PHASE = "modifyPhase";

    // Actions
    private static final String ACTION_CREATE_PHASE = "createPhase";
    private static final String ACTION_MODIFY_PHASE = "modifyPhase";
    private static final String ACTION_REMOVE_PHASE = "removePhase";
    private static final String ACTION_CONFIRM_REMOVE_PHASE = "confirmRemovePhase";

    // Infos
    private static final String INFO_PHASE_CREATED = "campaign.info.phase.created";
    private static final String INFO_PHASE_UPDATED = "campaign.info.phase.updated";
    private static final String INFO_PHASE_REMOVED = "campaign.info.phase.removed";

    // Session variable to store working values

    private Phase _phase;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_PHASES, defaultView = true )
    public String getManagePhases( HttpServletRequest request )
    {
        _phase = null;
        List<Phase> listPhases = PhaseHome.getPhasesList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_PHASE_LIST, listPhases, JSP_MANAGE_PHASES );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_PHASES, TEMPLATE_MANAGE_PHASES, model );
    }

    /**
     * Returns the form to create a phase
     *
     * @param request
     *            The Http request
     * @return the html code of the phase form
     */
    @View( VIEW_CREATE_PHASE )
    public String getCreatePhase( HttpServletRequest request )
    {
        _phase = ( _phase != null ) ? _phase : new Phase( );

        Map<String, Object> model = getModel( );
        model.put( MARK_PHASE, _phase );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( MARK_PHASE_TYPE_LIST, PhaseHome.getPhasesTypeReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_PHASE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_PHASE, TEMPLATE_CREATE_PHASE, model );
    }

    /**
     * Process the data capture form of a new phase
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_PHASE )
    public String doCreatePhase( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _phase, request, getLocale( ) );
        LocalDateTime startingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_STARTING_DATE_TIME ),
                DateTimeFormatter.ofPattern( Utils.dateFormat ) );
        LocalDateTime endingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_ENDING_DATE_TIME ),
                DateTimeFormatter.ofPattern( Utils.dateFormat ) );
        _phase.setEndingDate( endingDateTime );
        _phase.setStartingDate( startingDateTime );
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_PHASE ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _phase, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_PHASE );
        }

        PhaseHome.create( _phase );
        addInfo( INFO_PHASE_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_PHASES );
    }

    /**
     * Manages the removal form of a phase whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_PHASE )
    public String getConfirmRemovePhase( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PHASE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_PHASE ) );
        url.addParameter( PARAMETER_ID_PHASE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_PHASE, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a phase
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage phases
     */
    @Action( ACTION_REMOVE_PHASE )
    public String doRemovePhase( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PHASE ) );
        PhaseHome.remove( nId );
        addInfo( INFO_PHASE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_PHASES );
    }

    /**
     * Returns the form to update info about a phase
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_PHASE )
    public String getModifyPhase( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PHASE ) );

        if ( _phase == null || ( _phase.getId( ) != nId ) )
        {
            _phase = PhaseHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_PHASE, _phase );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( MARK_PHASE_TYPE_LIST, PhaseHome.getPhasesTypeReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_PHASE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_PHASE, TEMPLATE_MODIFY_PHASE, model );
    }

    /**
     * Process the change form of a phase
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_PHASE )
    public String doModifyPhase( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _phase, request, getLocale( ) );
        LocalDateTime startingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_STARTING_DATE_TIME ),
                DateTimeFormatter.ofPattern( Utils.dateFormat ) );
        LocalDateTime endingDateTime = LocalDateTime.parse( request.getParameter( PARAMETER_ENDING_DATE_TIME ),
                DateTimeFormatter.ofPattern( Utils.dateFormat ) );
        _phase.setEndingDate( endingDateTime );
        _phase.setStartingDate( startingDateTime );
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_PHASE ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _phase, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_PHASE, PARAMETER_ID_PHASE, _phase.getId( ) );
        }

        PhaseHome.update( _phase );
        addInfo( INFO_PHASE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_PHASES );
    }
}
