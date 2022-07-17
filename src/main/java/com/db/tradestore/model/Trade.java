package com.db.tradestore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRADE_STORE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "trade_id")
    private  String tradeId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "cnt_prty_id")
    private String counterPartyId;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "maturity_date")
    private Date maturityDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expired")
    private Character expired = 'N';

    public Trade(String tradeId, Integer version, String counterPartyId, String bookId, Date maturityDate, Date createdDate) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
    }
}
