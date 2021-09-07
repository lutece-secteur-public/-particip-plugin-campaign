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

import fr.paris.lutece.plugins.campaign.business.CampaignHome;
import fr.paris.lutece.plugins.campaign.business.Theme;
import fr.paris.lutece.plugins.campaign.business.ThemeHome;

/**
 * This class provides the user interface to manage Theme features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageThemes.jsp", controllerPath = "jsp/admin/plugins/campaign/", right = "CAMPAIGN_MANAGEMENT" )
public class ThemeJspBean extends AbstractManageCampaignsJspBean
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6454969911015792525L;
    // Templates
    private static final String TEMPLATE_MANAGE_THEMES = "/admin/plugins/campaign/manage_themes.html";
    private static final String TEMPLATE_CREATE_THEME = "/admin/plugins/campaign/create_theme.html";
    private static final String TEMPLATE_MODIFY_THEME = "/admin/plugins/campaign/modify_theme.html";

    // Parameters
    private static final String PARAMETER_ID_THEME = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_THEMES = "campaign.manage_themes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_THEME = "campaign.modify_theme.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_THEME = "campaign.create_theme.pageTitle";

    // Markers
    private static final String MARK_THEME_LIST = "theme_list";
    private static final String MARK_THEME = "theme";
    private static final String MARK_CAMPAIGN_LIST = "campaign_list";

    private static final String JSP_MANAGE_THEMES = "jsp/admin/plugins/campaign/ManageThemes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_THEME = "campaign.message.confirmRemoveTheme";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "campaign.model.entity.theme.attribute.";

    // Views
    private static final String VIEW_MANAGE_THEMES = "manageThemes";
    private static final String VIEW_CREATE_THEME = "createTheme";
    private static final String VIEW_MODIFY_THEME = "modifyTheme";

    // Actions
    private static final String ACTION_CREATE_THEME = "createTheme";
    private static final String ACTION_MODIFY_THEME = "modifyTheme";
    private static final String ACTION_REMOVE_THEME = "removeTheme";
    private static final String ACTION_CONFIRM_REMOVE_THEME = "confirmRemoveTheme";

    // Infos
    private static final String INFO_THEME_CREATED = "campaign.info.theme.created";
    private static final String INFO_THEME_UPDATED = "campaign.info.theme.updated";
    private static final String INFO_THEME_REMOVED = "campaign.info.theme.removed";

    // Session variable to store working values
    private Theme _theme;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_THEMES, defaultView = true )
    public String getManageThemes( HttpServletRequest request )
    {
        _theme = null;
        List<Theme> listThemes = ThemeHome.getThemesList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_THEME_LIST, listThemes, JSP_MANAGE_THEMES );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_THEMES, TEMPLATE_MANAGE_THEMES, model );
    }

    /**
     * Returns the form to create a theme
     *
     * @param request
     *            The Http request
     * @return the html code of the theme form
     */
    @View( VIEW_CREATE_THEME )
    public String getCreateTheme( HttpServletRequest request )
    {
        _theme = ( _theme != null ) ? _theme : new Theme( );

        Map<String, Object> model = getModel( );
        model.put( MARK_THEME, _theme );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_THEME ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_THEME, TEMPLATE_CREATE_THEME, model );
    }

    /**
     * Process the data capture form of a new theme
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_THEME )
    public String doCreateTheme( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _theme, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_THEME ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _theme, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_THEME );
        }

        ThemeHome.create( _theme );
        addInfo( INFO_THEME_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_THEMES );
    }

    /**
     * Manages the removal form of a theme whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_THEME )
    public String getConfirmRemoveTheme( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_THEME ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_THEME ) );
        url.addParameter( PARAMETER_ID_THEME, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_THEME, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a theme
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage themes
     */
    @Action( ACTION_REMOVE_THEME )
    public String doRemoveTheme( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_THEME ) );
        ThemeHome.remove( nId );
        addInfo( INFO_THEME_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_THEMES );
    }

    /**
     * Returns the form to update info about a theme
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_THEME )
    public String getModifyTheme( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_THEME ) );

        if ( _theme == null || ( _theme.getId( ) != nId ) )
        {
            _theme = ThemeHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_THEME, _theme );
        model.put( MARK_CAMPAIGN_LIST, CampaignHome.getCampaignsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_THEME ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_THEME, TEMPLATE_MODIFY_THEME, model );
    }

    /**
     * Process the change form of a theme
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_THEME )
    public String doModifyTheme( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _theme, request, getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_THEME ) )
        {
            throw new AccessDeniedException( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _theme, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_THEME, PARAMETER_ID_THEME, _theme.getId( ) );
        }

        ThemeHome.update( _theme );
        addInfo( INFO_THEME_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_THEMES );
    }
}
