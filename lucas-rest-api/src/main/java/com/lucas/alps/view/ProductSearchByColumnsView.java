package com.lucas.alps.view;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.ProductSearchByColumnsDetailsView;
import com.lucas.alps.viewtype.UserSearchByColumnsDetailsView;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.SpecialInstructions;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.entity.user.User;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.util.DateUtils;

public class ProductSearchByColumnsView {

    private List<Product> productList;

    public ProductSearchByColumnsView() {

    }

    public ProductSearchByColumnsView(List<Product> productList) {
        this.productList = productList;
    }

    @JsonView(ProductSearchByColumnsDetailsView.class)
    public Map<String, GridColumnView> getGrid() throws IOException {
        final LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();

        for (int i = 1; i <= BaseView.TOTAL_NO_OF_PRODUCT_GRID_COLUMNS; i++) {
            GridColumn gridColumn = new GridColumn();
            gridView.put(i + "", new GridColumnView(gridColumn));
        }

        for (Product product : CollectionsUtilService.nullGuard(productList)) {

            gridView.get("1")
                    .getValues()
                    .add((product.getProductCode() != null) ? (product.getProductCode()) : BaseView.ZERO_LENGTH_STRING);

            gridView.get("2")
                    .getValues()
                    .add((product.getName() != null) ? product.getName()	: BaseView.ZERO_LENGTH_STRING);

            gridView.get("3")
                    .getValues()
                    .add((product.getDescription() != null) ? product.getDescription() : BaseView.ZERO_LENGTH_STRING);

            gridView.get("4")
                    .getValues()
                    .add((product.getProductStatusCode() != null) ? product.getProductStatusCode() : BaseView.ZERO_LENGTH_STRING);

            gridView.get("5").getValues()
                    .add((product.getPerformSecondValidation() != null && product.getPerformSecondValidation())? "Yes" : "No");

            gridView.get("6").getValues()
                    .add((product.getCaptureLotNumber() != null && product.getCaptureLotNumber())? "Yes" : "No");


            gridView.get("7").getValues()
                    .add((product.getCaptureSerialNumber() != null && product.getCaptureSerialNumber())? "Yes" : "No");


            gridView.get("8").getValues()
                    .add((product.getIsBaseItem() != null && product.getIsBaseItem())? "Yes" : "No");

            gridView.get("9").getValues()
                    .add((product.getCaptureCatchWeight() != null && product.getCaptureCatchWeight())? "Yes" : "No");


            gridView.get("10").getValues()
                    .add((product != null  && product.getSpecialInstructionsList() != null
                            && product.getSpecialInstructionsList().isEmpty() )? "Yes" : "No");

            gridView.get("11").getValues()
                    .add((product.getHazardousMaterial() != null && product.getHazardousMaterial())? "Yes" : "No");


            gridView.get("12").getValues()
                    .add((product.getCustomerSkuNbr() != null)? product.getCustomerSkuNbr(): BaseView.ZERO_LENGTH_STRING );

            gridView.get("13")
                    .getValues()
                    .add((product.getManufacturerItemNbr() != null) ? product.getManufacturerItemNbr()	: BaseView.ZERO_LENGTH_STRING);

            gridView.get("14").getValues()
                    .add((product.getCaptureExpirationDate() != null && product.getCaptureExpirationDate())? "Yes" : "No");

        }

        return gridView;
    }

    public void setGrid(Map<String, GridColumnView> gridView)
            throws ParseException {
        int pageSize = gridView.get("1").getValues().size();
        productList = new ArrayList<Product>();

        for (int i = 0; i < pageSize; i++) {
            productList.add(new Product());
            productList.get(i).setProductCode(gridView.get("1").getValues().get(i));
            productList.get(i).setName(gridView.get("2").getValues().get(i));
            productList.get(i).setDescription(gridView.get("3").getValues().get(i));
            productList.get(i).setProductStatusCode(gridView.get("4").getValues().get(i));
            productList.get(i).setPerformSecondValidation(gridView.get("5").getValues().get(i).isEmpty());
            productList.get(i).setCaptureLotNumber(gridView.get("6").getValues().get(i).isEmpty());
            productList.get(i).setCaptureSerialNumber(gridView.get("7").getValues().get(i).isEmpty());
            productList.get(i).setIsBaseItem(gridView.get("8").getValues().get(i).isEmpty());
            productList.get(i).setCaptureCatchWeight(gridView.get("9").getValues().get(i).isEmpty());

            productList.get(i).setSpecialInstructionsList(new ArrayList<SpecialInstructions>());
            //productList.get(i).setSpecialInstructionsList(gridView.get("10").getValues().get(i));

            productList.get(i).setHazardousMaterial(gridView.get("11").getValues().get(i).isEmpty());
            productList.get(i).setCustomerSkuNbr(gridView.get("12").getValues().get(i));
            productList.get(i).setManufacturerItemNbr(gridView.get("13").getValues().get(i));
            productList.get(i).setCaptureExpirationDate(gridView.get("14").getValues().get(i).isEmpty());
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
