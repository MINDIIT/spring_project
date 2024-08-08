package shopping_admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class notice_attachments_dao {
	int attachment_id;
	int nidx;
	String file_name;
	String file_path;
	String upload_time;
}
