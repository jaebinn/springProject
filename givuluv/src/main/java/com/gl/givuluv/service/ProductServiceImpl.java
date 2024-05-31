package com.gl.givuluv.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.StoreMapper;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper pmapper;
	@Autowired
	private FileMapper fmapper;
	@Autowired
	private StoreMapper smapper;

	@Override
	public List<ProductDTO> getList() {
		return pmapper.getList();
	}

	@Override
	public ProductDTO getSList(int connectid) {
		return pmapper.getSList(connectid);
	}

	@Override
	public boolean deleteP(int productnum) {
		return false;
	}

	@Override
	public boolean insertP(ProductDTO product) {
		return false;
	}

	@Override
	public boolean updateP(ProductDTO product) {
		return false;
	}

	@Override
	public List<Map<String, Object>> getMProductList() {
		List<ProductDTO> productList = pmapper.getMProductList();
		System.out.println("productList: " + productList);

		List<Map<String, Object>> result = new ArrayList<>();
		String src = "/summernoteImage/";

		for (ProductDTO product : productList) {
			List<String> files = fmapper.getFileBySBoardnum(product.getConnectid());
			System.out.println("files: " + files);
			String systemname = src + files.get(0);

			StoreDTO store = smapper.getStoreBySBoardnum(product.getConnectid());
			System.out.println("스토어: " + store);

			Map<String, Object> map = new HashMap<>();
			map.put("product", product);
			map.put("systemname", systemname);
			map.put("store", store);
			result.add(map);
		}
		System.out.println("결과 " + result);
		return result;
	}

	@Override
	public int getProductnumByNameAndConnectid(String productname, int fBoardnum) {
		return pmapper.getProductnumByNameAndConnectid(productname, fBoardnum);
	}

	@Override
	public List<ProductDTO> getProduct(int productnum) {
		return pmapper.getProduct(productnum);
	}

	@Override
	public int[] getMConnectid() {
		return pmapper.getMConnectid();
	}

	@Override
	public int[] getMConnectidByCategory(String category) {
		return pmapper.getMConnectidByCategory(category);
	}
}
