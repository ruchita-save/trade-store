package com.db.tradestore.dto;

import com.db.tradestore.validation.LatestVersion;
import com.db.tradestore.validation.NotMatured;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString()
@NoArgsConstructor
//@LatestVersion
public class TradeDTO {

    @NotEmpty
    @Size(min=2,max=10)
    private  String tradeId;

    @Positive
    private Integer version;

    @NotEmpty
    @Size(min=2,max=10)
    private String counterPartyId;

    @NotEmpty
    @Size(min=2,max=10)
    private String bookId;

    @NotMatured // @Future could be used
    private Date maturityDate;

    private Date createdDate;
    private Character expired;

    private String errorMessage = "";

}
