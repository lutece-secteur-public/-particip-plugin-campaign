package fr.paris.lutece.plugins.campaign.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import fr.paris.lutece.plugins.campaign.business.Theme;

public interface ICampaignThemeService {

	Collection<Theme> getThemesListByCampaign(String codeCampaign);

	Map<String, List<Theme>> getThemesMapByCampaign();

	Theme findByCodeTheme(String codeTheme);
	
}
