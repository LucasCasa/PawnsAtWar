package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.model.Resource;

@Service
public class EmpireServiceImpl implements EmpireService{

	@Override
	public List<Resource> getResources() {
		//TODO request from persistance layer and update according to empire rate production
		ArrayList<Resource> list = new ArrayList<>();
		list.add(new Resource(0, 0));
		list.add(new Resource(1, 0));
		list.add(new Resource(2, 0));
		return list;
	}

}
