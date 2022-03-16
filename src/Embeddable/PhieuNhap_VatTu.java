package Embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhieuNhap_VatTu implements Serializable{

	@Column(name="MAPN")
	private String maPN;
	@Column(name="MAVT")
	private String maVT;
}
