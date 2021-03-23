package sampleOne.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections.map.HashedMap;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.Predicate;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;


@Component(service=Servlet.class,
property={
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
       
        "sling.servlet.extensions=json",
        "sling.servlet.resourceTypes="+ "sampleOne/components/structure/page",
        "sling.servlet.selectors=pages",
        "sling.servlet.paths="+"/bin/data_servlet"
})
public class SampleOneCustomServlet extends SlingSafeMethodsServlet{

	//This is the resourceType for the pages i want to query(In the jcr:content properties from content folder)
	private static String PAGES_RESOURCE_TYPE = "sampleOne/components/structure/page";
	
	
	
	@Reference
	private QueryBuilder queryBuilder; //This is my reference to query builder
	
	

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	response.setContentType("application/json; charset=UTF-8");

	//to get the resource of the current request(Get the resource from the current request path)
	//storing the resource object from the request path
	final Resource resource = request.getResource();
	
	//Get a resource Resolver from the request
	//Resource resolver to perform resource related tasks
	
	ResourceResolver resourceResolver = request.getResourceResolver();
	
	
	//Map to build QueryBuilder
	Map<String, String> map = new HashMap<String,String>();
	map.put("path", resource.getParent().getPath()); //This maps the path of the query to the resource(aka -> jcr:content) parent which is the "home_page" itself -> ('/content/sampleOne/en/home-page')
	map.put("1_property","sling:resourceType"); //1_property we want to get returned that should be present in each node we want to query that is "sling:resourceType"
	map.put("1_property.value", PAGES_RESOURCE_TYPE);
	map.put("p.guessTotal","true");
	map.put("p.limit","-1" );//don't limit the queries
	//Building done
	
	//Now let's build the query
	//query takes the map
	//query also needs a session so we adapts it with resourceResolver
	Query query = queryBuilder.createQuery(PredicateGroup.create(map),resourceResolver.adaptTo(Session.class));
	//Store the results
	SearchResult results = query.getResult();
	
	
	//Create a list of resources to get Resources from the results
	List<Resource> resources = new ArrayList<Resource>();
	
	//QueryBuilder has a leaking resource resolver so work around is required
	ResourceResolver leakingResourceResolver = null;
	
	try {
		//For each hit we gonna get a resource  it's leaking i.e null we gonna take it in leakingResourceResolver
		//but we want the path so..when not null
		for(final Hit hit : results.getHits()) {
			if(leakingResourceResolver == null) {
				leakingResourceResolver = hit.getResource().getResourceResolver();
			}
			
			//else we gonna add it through resource resolver
			resources.add(resourceResolver.getResource(hit.getPath()));
			
		}
		
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	finally {
		if(leakingResourceResolver != null) {
			leakingResourceResolver.close();
		}
	}
	
	
	response.getWriter().write("Number of pages : " + Integer.toString(resources.size()));
	}
}
