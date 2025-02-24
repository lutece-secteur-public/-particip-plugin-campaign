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

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminAuthenticationService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import java.util.List;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.web.LocalVariables;
import java.io.IOException;
import fr.paris.lutece.plugins.campaign.business.Theme;
import fr.paris.lutece.plugins.campaign.business.ThemeHome;

/**
 * This is the business class test for the object Theme
 */
public class ThemeJspBeanTest extends LuteceTestCase
{
    private static final int IDCAMPAIGN1 = 1;
    private static final int IDCAMPAIGN2 = 2;
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final boolean ACTIVE1 = true;
    private static final boolean ACTIVE2 = false;
    private static final String FRONTRGB1 = "FrontRgb1";
    private static final String FRONTRGB2 = "FrontRgb2";
    private static final int IMAGEFILE1 = 1;
    private static final int IMAGEFILE2 = 2;

    public void testJspBeans( ) throws AccessDeniedException
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        MockHttpServletResponse response = new MockHttpServletResponse( );
        MockServletConfig config = new MockServletConfig( );

        // display admin Theme management JSP
        ThemeJspBean jspbean = new ThemeJspBean( );
        String html = jspbean.getManageThemes( request );
        assertNotNull( html );

        // display admin Theme creation JSP
        html = jspbean.getCreateTheme( request );
        assertNotNull( html );

        // action create Theme
        request = new MockHttpServletRequest( );

        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN1 ) );
        request.addParameter( "title", TITLE1 );
        request.addParameter( "description", DESCRIPTION1 );
        request.addParameter( "active", String.valueOf( ACTIVE1 ) );
        request.addParameter( "front_rgb", FRONTRGB1 );
        request.addParameter( "image_file", String.valueOf( IMAGEFILE1 ) );
        request.addParameter( "action", "createTheme" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "createTheme" ) );
        request.setMethod( "POST" );
        response = new MockHttpServletResponse( );
        AdminUser adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // display modify Theme JSP
        request = new MockHttpServletRequest( );
        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN1 ) );
        request.addParameter( "title", TITLE1 );
        request.addParameter( "description", DESCRIPTION1 );
        request.addParameter( "active", String.valueOf( ACTIVE1 ) );
        request.addParameter( "front_rgb", FRONTRGB1 );
        request.addParameter( "image_file", String.valueOf( IMAGEFILE1 ) );
        List<Integer> listIds = ThemeHome.getIdThemesList( );
        assertTrue( !listIds.isEmpty( ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new ThemeJspBean( );

        assertNotNull( jspbean.getModifyTheme( request ) );

        // action modify Theme
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN2 ) );
        request.addParameter( "title", TITLE2 );
        request.addParameter( "description", DESCRIPTION2 );
        request.addParameter( "active", String.valueOf( ACTIVE2 ) );
        request.addParameter( "front_rgb", FRONTRGB2 );
        request.addParameter( "image_file", String.valueOf( IMAGEFILE2 ) );
        request.setRequestURI( "jsp/admin/plugins/example/ManageThemes.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createTheme, qui est l'action par défaut
        request.addParameter( "action", "modifyTheme" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "modifyTheme" ) );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // get remove Theme
        request = new MockHttpServletRequest( );
        // request.setRequestURI("jsp/admin/plugins/example/ManageThemes.jsp");
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new ThemeJspBean( );
        request.addParameter( "action", "confirmRemoveTheme" );
        assertNotNull( jspbean.getModifyTheme( request ) );

        // do remove Theme
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.setRequestURI( "jsp/admin/plugins/example/ManageThemets.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createTheme, qui est l'action par défaut
        request.addParameter( "action", "removeTheme" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "removeTheme" ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        request.setMethod( "POST" );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

    }
}
