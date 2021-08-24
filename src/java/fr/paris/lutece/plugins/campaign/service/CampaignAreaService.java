package fr.paris.lutece.plugins.campaign.service;

import fr.paris.lutece.plugins.campaign.business.AreaHome;
import fr.paris.lutece.util.ReferenceList;

public class CampaignAreaService {
	
	
	private CampaignAreaService ( )
	{	
		
	}
	
	/**
     * Load the data of the area code by campaign code and returns them as a referenceList
     * 
     * @param campaignCode the campaign code
     * @return the referenceList which contains the data of the area code
     */
    public static ReferenceList getAreasReferenceList( String campaignCode )
    {
        return AreaHome.getAreasReferenceList( campaignCode );
    } 
}
