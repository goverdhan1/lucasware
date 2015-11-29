/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */

package com.lucas.alps.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.ProductCustomClassificationView;
import com.lucas.alps.view.ProductSearchByColumnsView;
import com.lucas.alps.viewtype.ProductCustomClassificationDetailsView;
import com.lucas.alps.viewtype.ProductSearchByColumnsDetailsView;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.ProductCustomClassification;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.product.ProductService;
import com.lucas.services.search.SearchAndSortCriteria;


/**
 * Controller class for Products and its related classes.
 *
 */
@Controller
@RequestMapping(value="/products")
public class ProductController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private final ProductService productService;
	
    @Inject
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
    
    
    /**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/products/productlist/search
     * sample JSON expected as input to this method
		{
		  "pageSize": 3,
		  "pageNumber": 0,
		  "searchMap": {
		    "name": [
		      "99-4937",
		      "99-4938",
		      "99-4939"
		    ]
		  },
		  "sortMap": {
		    "name": "ASC"
		  }
		}

returns the result:

		{
		  "status": "success",
		  "code": "200",
		  "message": "Request processed successfully",
		  "level": null,
		  "uniqueKey": null,
		  "token": null,
		  "explicitDismissal": null,
		  "data": {
		    "grid": {
		      "1": {
		        "values": [
		          "99-4937",
		          "99-4938",
		          "99-4939"
		        ]
		      },
		      "2": {
		        "values": [
		          "TORO PULLEY",
		          "Water / Fuel Filter ASM",
		          "HOUSING ASM-REDUCER RH"
		        ]
		      },
		      "3": {
		        "values": [
		          "Available",
		          "Available",
		          "Available"
		        ]
		      },
		      "4": {
		        "values": [
		          "99-4937",
		          "99-4938",
		          "99-4939"
		        ]
		      },
		      "5": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "6": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "7": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "8": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "9": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "10": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "11": {
		        "values": [
		          "1",
		          "1",
		          "1"
		        ]
		      },
		      "12": {
		        "values": [
		          "Yes",
		          "No",
		          "No"
		        ]
		      }
		    }
		  }
		}

     */
	@ResponseView(ProductSearchByColumnsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/productlist/search")
    @ResponseBody
    public ApiResponse<ProductSearchByColumnsView> getProductListBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

        final ApiResponse<ProductSearchByColumnsView> apiResponse = new ApiResponse<ProductSearchByColumnsView>();
        List<Product> productList = null;
        ProductSearchByColumnsView productSearchBycolumnsView = null;
        try {
        	productList = productService.getProductListBySearchAndSortCriteria(searchAndSortCriteria);
            productSearchBycolumnsView = new ProductSearchByColumnsView(productList);
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }
        apiResponse.setData(productSearchBycolumnsView);

		LOG.debug("Size{}",productList.size());
        return apiResponse;
    }
	
	/**
	   * request for getting all the product custom classification records.
	   */
	  @ResponseView(ProductCustomClassificationDetailsView.class)
	  @RequestMapping(value = "/productcustomclassifications", method = RequestMethod.GET)
	  @ResponseBody
	  public ApiResponse<List<ProductCustomClassificationView>> getAllProductCustomClassification() {
	    LOG.debug("REST request to get all Product Custom Classification");
	    final ApiResponse<List<ProductCustomClassificationView>> apiResponse = new ApiResponse<List<ProductCustomClassificationView>>();

	    List<ProductCustomClassification> productCustomClassificationList = null;
	    List<ProductCustomClassificationView> productCustomClassificationViewList = new ArrayList<ProductCustomClassificationView>();
	    try {
	      productCustomClassificationList = productService.getAllProductCustomClassification();
	      for (ProductCustomClassification productCustomClassification : productCustomClassificationList) {
	        productCustomClassificationViewList.add(new ProductCustomClassificationView(productCustomClassification));
	      }
	      apiResponse.setData(productCustomClassificationViewList);
	      LOG.debug("Size{}", productCustomClassificationViewList.size());

	    } catch (Exception e) {
	      throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
	    }
	    
	    return apiResponse;
	  }
}
