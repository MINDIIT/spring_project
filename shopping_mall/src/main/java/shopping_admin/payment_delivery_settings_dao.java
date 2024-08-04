package shopping_admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class payment_delivery_settings_dao {
	int sidx;
	String bank_transfer_bank_name,bank_transfer_account_number,credit_card_payment,mobile_payment,book_voucher_payment,min_payment_point,max_payment_point,cash_receipt_usage;
	String delivery_company_name,delivery_fee,preferred_delivery_date_usage;  
}
