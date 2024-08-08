package shopping_admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class notice_dao {
	int nidx;
	int view_count;
	String admin_id;
	String admin_name;
	String notice_title;
	String notice_content;
	String is_pinned;
	String notice_create;
	String notice_update;
}
