package Embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhieuXuat_VatTu implements Serializable{

	@Column(name="MAPX")
	private String maPX;
	
	@Column(name="MAVT")
	private String maVT;
}
