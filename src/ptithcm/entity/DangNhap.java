package ptithcm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="DangNhap",
uniqueConstraints= @UniqueConstraint(columnNames= {"TenDangNhap"}))
public class DangNhap{
	
	@Id
	@GeneratedValue
	@Column(name="Id_DangNhap")
	private Integer idDangNhap;
	
	@OneToOne
	@JoinColumn(name="TenDangNhap" ,referencedColumnName="maNV")
	private NhanVien nhanVien;
	
	@Column(name="MatKhau")
	private String matKhau;

	public Integer getIdDangNhap() {
		return idDangNhap;
	}

	public void setIdDangNhap(Integer idDangNhap) {
		this.idDangNhap = idDangNhap;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	
	
}
