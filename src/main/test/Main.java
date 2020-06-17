package main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.service.ComTranser;
import main.java.service.impl.ListTranser;

@SuppressWarnings("unchecked")
public class Main {

	public static void main(String[] args) {

		List<En> ens = new ArrayList<>();
		En en = new En();
		en.setId(1);
		en.setName("x");
		ens.add(en);
//		En en2 = new En();
//		en2.setId(2);
//		en2.setName("y");
//		ens.add(en2);
//		List<Ti> tis=null;
		List<Ti> tis = new ArrayList<>();
//		 Ti ti=new Ti();
//		 ti.setId(1);
//		 ti.setName("y");
//		 tis.add(ti);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("id", "id");
		fields.put("name", "name");
		
		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		
		ComTranser<En, Ti,Object,Object> data = new ListTranser();
		List<Ti>[] es = data.sync(ens, tis, new Ti().getClass().getName(), fields,compares);
		System.out.println(es.length);
		for(int i=0;i<es.length;i++) {
			List<Ti> ls = es[i];
			for(int j=0;j<ls.size();j++) {
				System.out.println(ls.get(j).getId() + ": " + ls.get(j).getName());
			}
		}
		
	}

}
