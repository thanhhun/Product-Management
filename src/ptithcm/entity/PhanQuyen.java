package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PhanQuyen")
public class PhanQuyen {
	@Id
	@Column(name="Id_PhanQuyen")
	private Integer idPhanQuyen;
	@Column(name="TenPhanQuyen")
	private String tenPhanQuyen;
	
	@OneToMany(mappedBy="phanQuyen",fetch=FetchType.EAGER)
	private Collection<NhanVien> nhanVien;



	public Integer getIdPhanQuyen() {
		return idPhanQuyen;
	}

	public void setIdPhanQuyen(Integer idPhanQuyen) {
		this.idPhanQuyen = idPhanQuyen;
	}

	public String getTenPhanQuyen() {
		return tenPhanQuyen;
	}

	public void setTenPhanQuyen(String tenPhanQuyen) {
		this.tenPhanQuyen = tenPhanQuyen;
	}

	public Collection<NhanVien> getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(Collection<NhanVien> nhanVien) {
		this.nhanVien = nhanVien;
	}
	
	
}
