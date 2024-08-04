package shopping_admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class products_dao {
	int pidx;
	String admin_id;
	String classification_code,main_menu_code,main_menu_name,product_code;
	String product_name,product_additional_description,product_price,discount_rate,discount_price,product_stock;
	String is_available, is_sold_out_early,main_product_image1,main_product_image2,main_product_image3,product_detailed_description,product_insert_time;
}
