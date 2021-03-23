package sampleOne.core.models;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import sampleOne.core.services.SideNavigationService;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SideNavigationModel {

	List<String> categorySizes;

	@OSGiService
	SideNavigationService sideNavigationService;

	@PostConstruct
	protected void init() {
		try {
			categorySizes = sideNavigationService.getCategorySizes("sidenav");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<String> getCategorySizes() {
		return categorySizes;
	}
	
}
