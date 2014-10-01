package com.dbaneman.constgen.example;

public class DinosaurSchema {
	public static class Dinosaurs {
		public static final String NAME = "dinosaurs";
		public static class Paleontologists {
			public static final String NAME = Dinosaurs.NAME + "." + "paleontologists";
			public static final String ID = Dinosaurs.Paleontologists.NAME + "." + "id";
			public static final String LAST_NAME = Dinosaurs.Paleontologists.NAME + "." + "last_name";
			public static final String FIRST_NAME = Dinosaurs.Paleontologists.NAME + "." + "first_name";
			public static final String RESEARCH_INSTITUTE = Dinosaurs.Paleontologists.NAME + "." + "research_institute";
		}
		public static class Dinosaurs1 {
			public static final String NAME = Dinosaurs.NAME + "." + "dinosaurs1";
			public static final String ID = Dinosaurs.Dinosaurs1.NAME + "." + "id";
			public static final String SPECIES_NAME = Dinosaurs.Dinosaurs1.NAME + "." + "species_name";
			public static final String EPOCH = Dinosaurs.Dinosaurs1.NAME + "." + "epoch";
			public static final String YEAR_DISCOVERED = Dinosaurs.Dinosaurs1.NAME + "." + "year_discovered";
			public static final String DISCOVERED_BY = Dinosaurs.Dinosaurs1.NAME + "." + "discovered_by";
			public static final String PRIMARY_PREY = Dinosaurs.Dinosaurs1.NAME + "." + "primary_prey";
		}
		public static class ResearchInstitutes {
			public static final String NAME = Dinosaurs.NAME + "." + "research_institutes";
			public static final String ID = Dinosaurs.ResearchInstitutes.NAME + "." + "id";
			public static final String INSTITUTE_NAME = Dinosaurs.ResearchInstitutes.NAME + "." + "institute_name";
			public static final String LOCATION = Dinosaurs.ResearchInstitutes.NAME + "." + "location";
		}
	}
}
