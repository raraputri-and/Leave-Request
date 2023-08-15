package id.co.mii.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {
    private String name;
    private String url;
    private String type;
    private long size;
    private Integer leaveRequestId;
}
