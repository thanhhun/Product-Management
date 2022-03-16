package Embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatHang_VatTu implements Serializable{

	@Column(name="MasoDDH")
	private String maDDH;
	@Column(name="MAVT")
	private String maVT;
}
