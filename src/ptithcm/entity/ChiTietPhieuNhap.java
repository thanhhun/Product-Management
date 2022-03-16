package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "CTPN",
uniqueConstraints= @UniqueConstraint(columnNames= {"MaPN","MaVT"}))
public class ChiTietPhieuNhap {

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="MaPN")
	private PhieuNhap phieuNhap;
	
	@ManyToOne
	@JoinColumn(name="MaVT")
	private VatTu vatTu;

	@Column(name = "SOLUONG")
	private Integer soLuong;
	@Column(name = "DONGIA")
	private Float donGia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PhieuNhap getPhieuNhap() {
		return phieuNhap;
	}

	public void setPhieuNhap(PhieuNhap phieuNhap) {
		this.phieuNhap = phieuNhap;
	}

	public VatTu getVatTu() {
		return vatTu;
	}

	public void setVatTu(VatTu vatTu) {
		this.vatTu = vatTu;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public Float getDonGia() {
		return donGia;
	}

	public void setDonGia(Float donGia) {
		this.donGia = donGia;
	}

}
