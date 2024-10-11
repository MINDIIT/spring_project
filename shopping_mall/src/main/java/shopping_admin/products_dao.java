package shopping_admin;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class products_dao {
	int pidx;
	String admin_id;
	String classification_code;
	String main_menu_code;
	String main_menu_name;
	String product_code;
	String product_name;
	String product_additional_description;
	String product_price;
	String discount_rate ;
	String discount_price ;
	String product_stock;
	String is_available;
	String is_sold_out_early;
	String product_detailed_description;
	String product_insert_time;
	String search_part, search_word;
}
