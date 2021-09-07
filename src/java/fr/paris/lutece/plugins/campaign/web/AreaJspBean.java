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

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.campaign.business.Area;
import fr.paris.lutece.plugins.campaign.business.AreaHome;
import fr.paris.lutece.plugins.campaign.business.CampaignHome;

/**
 * This class provides the user interface to manage Area features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAreas.jsp", controllerPath = "jsp/admin/plugins/campaign/", right = "CAMPAIGN_MANAGEMENT" )
public class AreaJspBean extends AbstractManageCampaignsJspBean
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8585257960329982851L;
    // Templates
    private static final String TEMPLATE_MANAGE_AREAS = "/admin/plugins/campaign/manage_areas.html";
    private static final String TEMPLATE_CREATE_AREA = "/admin/plugins/campaign/create_area.html";
    private static final String TEMPLATE_MODIFY_AREA = "/admin/plugins/campaign/modify_area.html";

    // Parameters
    private static final String PARAMETER_ID_AREA = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_AREAS = "campaign.manage_areas.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_AREA = "campaign.modify_area.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_AREA = "campaign.create_area.pageTitle";

    // Markers
    private static final String MARK_AREA_LIST = "area_list";
    private static final String MARK_AREA = "area";
    private static final String MARK_CAMPAIGN_LIST = "campaign_list";

    private static final String JSP_MANAGE_AREAS = "jsp/admin/plugins/campaign/ManageAreas.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_AREA = "campaign.message.confirmRemoveArea";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "campaign.model.entity.area.attribute.";

    // Views
    private static final String VIEW_MANAGE_AREAS = "manageAreas";
    private static final String VIEW_CREATE_AREA = "createArea";
    private static final String VIEW_MODIFY_AREA = "modifyArea";

    // Actions
    private static final String ACTION_CREATE_AREA = "createArea";
    private static final String ACTION_MODIFY_AREA = "modifyArea";
    private static final String ACTION_REMOVE_AREA = "removeArea";
    private static final String ACTION_CONFIRM_REMOVE_AREA = "confirmRemoveArea";

    // Infos
    private static final String INFO_AREA_CREATED = "campaign.info.area.created";
    private static final String INFO_AREA_UPDATED = "campaign.info.area.updated";
    private static final String INFO_AREA_REMOVED = "campaign.info.area.removed";

    // Session variable to store working values
    private Area _area;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_AREAS, defaultView = true )
    public String getManageAreas( HttpServletRequest request )
    {
        _area = null;
        List<Area> listAreas = AreaHome.getAreasList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_AREA_LIST, listAreas, JSP_MANAGE_AREAS );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_AREAS, TEMPLATE_MANAGE_AREAS, model );
    }

    /**
     * Returns the form to create a area
     *
     * @param request
     *            The Http request
     * @return the html code of the area form
     */
    @View( VIEW_CREATE_AREA )
    public String getCreateArea( HttpServletRequest request )
    {
        _area = ( _area != null ) ? _area : new Area( );

        Map<String, Object> model = getModel( );
        model.put( MARK_AREA, _area );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_AREA ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_AREA, TEMPLATE_CREATE_AREA, model );
    }

    /**
     * Process the data capture form of a new area
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_AREA )
    public String doCreateArea( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _area, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_AREA ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _area, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_AREA );
        }

        AreaHome.create( _area );
        addInfo( INFO_AREA_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_AREAS );
    }

    /**
     * Manages the removal form of a area whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_AREA )
    public String getConfirmRemoveArea( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AREA ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_AREA ) );
        url.addParameter( PARAMETER_ID_AREA, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_AREA, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a area
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage areas
     */
    @Action( ACTION_REMOVE_AREA )
    public String doRemoveArea( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AREA ) );
        AreaHome.remove( nId );
        addInfo( INFO_AREA_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_AREAS );
    }

    /**
     * Returns the form to update info about a area
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_AREA )
    public String getModifyArea( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AREA ) );

        if ( _area == null || ( _area.getId( ) != nId ) )
        {
            _area = AreaHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_AREA, _area );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_AREA ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_AREA, TEMPLATE_MODIFY_AREA, model );
    }

    /**
     * Process the change form of a area
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_AREA )
    public String doModifyArea( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _area, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_AREA ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _area, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_AREA, PARAMETER_ID_AREA, _area.getId( ) );
        }

        AreaHome.update( _area );
        addInfo( INFO_AREA_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_AREAS );
    }
}
