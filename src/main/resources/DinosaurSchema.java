package com.dbaneman.constgen.example;

public class DinosaurSchema {
	public static class Dinosaurs {
		public static final String NAME = "dinosaurs";
		public static class Paleontologists {
			public static final String NAME = "dinosaurs.paleontologists";
			public static final String ID = "dinosaurs.paleontologists.id";
			public static final String LAST_NAME = "dinosaurs.paleontologists.last_name";
			public static final String FIRST_NAME = "dinosaurs.paleontologists.first_name";
			public static final String RESEARCH_INSTITUTE = "dinosaurs.paleontologists.research_institute";
		}
		public static class Dinosaurs1 {
			public static final String NAME = "dinosaurs.dinosaurs";
			public static final String ID = "dinosaurs.dinosaurs.id";
			public static final String SPECIES_NAME = "dinosaurs.dinosaurs.species_name";
			public static final String EPOCH = "dinosaurs.dinosaurs.epoch";
			public static final String YEAR_DISCOVERED = "dinosaurs.dinosaurs.year_discovered";
			public static final String DISCOVERED_BY = "dinosaurs.dinosaurs.discovered_by";
			public static final String PRIMARY_PREY = "dinosaurs.dinosaurs.primary_prey";
		}
		public static class ResearchInstitutes {
			public static final String NAME = "dinosaurs.research_institutes";
			public static final String ID = "dinosaurs.research_institutes.id";
			public static final String INSTITUTE_NAME = "dinosaurs.research_institutes.institute_name";
			public static final String LOCATION = "dinosaurs.research_institutes.location";
		}
	}
}
