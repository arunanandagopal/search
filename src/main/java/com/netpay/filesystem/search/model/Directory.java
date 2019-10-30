package com.netpay.filesystem.search.model;


import javax.persistence.*;

@Entity
@Table(schema = "public", name="directory")
public class Directory {
	
	@Id
    @Column(name = "path", nullable = false, columnDefinition = "ltree")
    private String path;

	public String getPath() {
		return path;
	}
}
