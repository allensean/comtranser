package test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.java.service.ComTranser;
import main.java.service.impl.ListTranser;

@SuppressWarnings("unchecked")
public class ComTranserTest {

	@Test
	public void batchEx() {
		testTransListToListArrayEmptySources();
		testTransListToListArrayEmptyTargets();
		TestTransListToListArrayMoreSources();
		TestTransListToListArrayMoreTargets();
		TestTransListToListArraySameNumDiffEle();
		TestTransListToListArrayUseTargetsValue();

	}

	void testTransListToListArrayEmptySources() {

		System.out.println("****************testTransListToListArrayEmptySources*****************");

		List<Source> sources = new ArrayList<>();

		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setId(1);
		target.setName("x");
		targets.add(target);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");

		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares);

		outprint(es);

	}

	void testTransListToListArrayEmptyTargets() {

		System.out.println("****************testTransListToListArrayEmptyTargets*****************");

		List<Source> sources = new ArrayList<>();
		Source source = new Source();
		source.setId(1);
		source.setName("x");
		sources.add(source);
		List<Target> targets = new ArrayList<>();

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");

		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares);

		outprint(es);

	}

	void TestTransListToListArrayMoreSources() {

		System.out.println("****************TestTransListToListArrayMoreSources*****************");

		List<Source> sources = new ArrayList<>();
		Source source = new Source();
		source.setId(1);
		source.setName("x");
		source.setValue(10);
		sources.add(source);

		Source source1 = new Source();
		source1.setId(2);
		source1.setName("y");
		source1.setValue(20);
		sources.add(source1);

		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setId(1);
		target.setName("x");
		target.setValue(100);
		targets.add(target);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");

		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");
		

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares);

		outprint(es);
	}

	void TestTransListToListArrayMoreTargets() {

		System.out.println("****************TestTransListToListArrayMoreTargets*****************");

		List<Source> sources = new ArrayList<>();

		Source source = new Source();
		source.setId(1);
		source.setName("x");
		source.setValue(10);
		sources.add(source);

		Source source1 = new Source();
		source1.setId(2);
		source1.setName("y");
		source1.setValue(20);
		sources.add(source1);

		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setId(1);
		target.setName("x");
		target.setValue(100);
		targets.add(target);

		Target target1 = new Target();
		target1.setId(2);
		target1.setName("y");
		target1.setValue(200);
		targets.add(target1);

		Target target2 = new Target();
		target2.setId(3);
		target2.setName("z");
		target2.setValue(300);
		targets.add(target2);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");
		
		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares);

		outprint(es);
	}
	
	void TestTransListToListArraySameNumDiffEle() {

		System.out.println("****************TestTransListToListArraySameNumDiffEle*****************");

		List<Source> sources = new ArrayList<>();

		Source source = new Source();
		source.setId(1);
		source.setName("x");
		source.setValue(10);
		sources.add(source);

		Source source1 = new Source();
		source1.setId(2);
		source1.setName("y");
		source1.setValue(20);
		sources.add(source1);
		
		Source source2 = new Source();
		source2.setId(3);
		source2.setName("m");
		source2.setValue(30);
		sources.add(source2);

		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setId(1);
		target.setName("x");
		target.setValue(100);
		targets.add(target);

		Target target1 = new Target();
		target1.setId(2);
		target1.setName("y");
		target1.setValue(200);
		targets.add(target1);

		Target target2 = new Target();
		target2.setId(3);
		target2.setName("z");
		target2.setValue(300);
		targets.add(target2);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");

		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares);

		outprint(es);
	}
	
	void TestTransListToListArrayUseTargetsValue() {

		System.out.println("****************TestTransListToListArrayUseTargetsValue*****************");

		List<Source> sources = new ArrayList<>();

		Source source = new Source();
		source.setId(1);
		source.setName("x");
		source.setValue(10);
		sources.add(source);

		Source source1 = new Source();
		source1.setId(2);
		source1.setName("y");
		source1.setValue(20);
		sources.add(source1);
		
		Source source2 = new Source();
		source2.setId(3);
		source2.setName("m");
		source2.setValue(30);
		sources.add(source2);

		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setId(1);
		target.setName("x");
		target.setValue(100);
		targets.add(target);

		Target target1 = new Target();
		target1.setId(2);
		target1.setName("y");
		target1.setValue(200);
		targets.add(target1);

		Target target2 = new Target();
		target2.setId(3);
		target2.setName("z");
		target2.setValue(300);
		targets.add(target2);

		Map<Object, Object> fields = new HashMap<Object, Object>();
		fields.put("name", "name");
		fields.put("value", "value");

		Map<Object, Object> compares = new HashMap<Object, Object>();
		compares.put("name", "name");

		ComTranser<Source, Target, Object, Object> data = new ListTranser();
		List<Target>[] es = data.transToListArray(sources, targets, new Target().getClass().getName(), fields,
				compares,new Target().getClass().getName());

		outprint(es);
	}

	void outprint(List<Target>[] es) {
		for (int i = 0; i < es.length; i++) {
			if (es[i] == null) {
				continue;
			}
			List<Target> ls = es[i];
			for (int j = 0; j < ls.size(); j++) {
				System.out.println("ele:" + ls.get(j).getId() + ", " + ls.get(j).getName()+","+ls.get(j).getValue());
			}
		}

		List<Target> adds = es[0];
		List<Target> updates = es[1];
		List<Target> deletes = es[2];
		System.out.print("*******");
		System.out.print("adds size:");
		System.out.println(adds == null ? "null" : adds.size());
		if (adds != null) {
			adds.forEach(e -> {
				System.out.println("adds:" + e.getId() + ", " + e.getName()+","+e.getValue());
			});
		}
		System.out.print("*******");
		System.out.print("udpates size:");
		System.out.println(updates == null ? "empty" : +updates.size());
		if (updates != null) {
			for (Target ts : updates) {
				System.out.println("udpates:" + ts.getId() + ", " + ts.getName()+","+ts.getValue());
			}
		}
		if (deletes != null) {
			System.out.print("*******");
			System.out.print("deletes size:");
			System.out.println("deletes size:" + deletes == null ? "empty" : +deletes.size());
			for (Target ts : deletes) {
				System.out.println("deletes:" + ts.getId() + ", " + ts.getName()+","+ts.getValue());
			}
		}
	}

}
