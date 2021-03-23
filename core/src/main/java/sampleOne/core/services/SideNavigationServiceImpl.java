package sampleOne.core.services;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import sampleOne.core.dao.BurgerStopDao;

@Component(immediate = true,service = SideNavigationService.class)
public class SideNavigationServiceImpl implements SideNavigationService{
	
	@Reference
	BurgerStopDao burgerStopDao;
	
	
	@Override
	@Activate
	@Modified
	public List<String> getCategorySizes(String dataSourceName) throws Exception {
		// TODO Auto-generated method stub
		return burgerStopDao.getSizeCategories(dataSourceName);
	}

}
