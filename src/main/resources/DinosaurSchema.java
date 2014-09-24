package com.dbaneman.constgen.example;

public class DinosaurSchema {
	public static class Dinosaurs {
		public static final String NAME = "dinosaurs";
		public static class Dinosaurs1 {
			public static final String NAME = "dinosaurs";
			public static final String ID = "id";
			public static final String SPECIES_NAME = "species_name";
			public static final String EPOCH = "epoch";
			public static final String YEAR_DISCOVERED = "year_discovered";
			public static final String DISCOVERED_BY = "discovered_by";
			public static final String PRIMARY_PREY = "primary_prey";
		}
		public static class Paleontologists {
			public static final String NAME = "paleontologists";
			public static final String ID = "id";
			public static final String LAST_NAME = "last_name";
			public static final String FIRST_NAME = "first_name";
			public static final String RESEARCH_INSTITUTE = "research_institute";
		}
		public static class ResearchInstitutes {
			public static final String NAME = "research_institutes";
			public static final String ID = "id";
			public static final String INSTITUTE_NAME = "institute_name";
			public static final String LOCATION = "location";
		}
	}
}
