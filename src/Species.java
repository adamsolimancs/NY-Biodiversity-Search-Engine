package project3;
import java.util.ArrayList;
/**
 * This class represents a Species object. A valid Species object has a category, taxonomic group, taxonomic sub group,
 * scientific name, common name, and NY listing status.
 * Null or empty values are not allowed.
 * All data is from data.ny.gov, "Biodiversity by County - Distribution of Animals, Plants and Natural Communities."
 * @author Adam Soliman
 * @version 3-6-24
 */
public class Species implements Comparable<Species> {
	
	// Fields
	private String category;
    private String taxonomicGroup;
    private String taxonomicSubGroup;
    private String scientificName;
    private String commonName;
    private String NYListingStatus;
    ArrayList<String> counties = new ArrayList<>();
	
	/**
	 * Six argument constructor for the Species class: constructs a new Species object with specified fields.
	 * Input Strings should not be null or empty.
	 * @param category	Category name for this species.
	 * @param taxonomicGroup	Taxonomic group name for this species.
	 * @param taxnomicSubGroup	Taxonomic sub group for this species.
	 * @param scientificName	Scientific name for this species.
	 * @param commonName	Category name for this species.
	 * @param NYListingStatus	New York State Listing Status for this species.
	 * @throws IllegalArgumentException	if any input value is null or an empty String.
	 */
	public Species(String category,
            String taxonomicGroup,
            String taxonomicSubGroup,
            String scientificName,
            String commonName,
            String NYListingStatus
            ) throws IllegalArgumentException {
		
		if (category == null || taxonomicGroup == null || taxonomicSubGroup == null || scientificName == null 
				|| commonName == null || NYListingStatus == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		} else if (category.equals("") || taxonomicGroup.equals("") || taxonomicSubGroup.equals("") || scientificName.equals("") 
				|| commonName.equals("") || NYListingStatus.equals("")) {
			throw new IllegalArgumentException("Arguments cannot be empty strings");
		} else {
			this.category = category;
			this.taxonomicGroup = taxonomicGroup;
			this.taxonomicSubGroup = taxonomicSubGroup;
			this.scientificName = scientificName;
			this.commonName = commonName;
			this.NYListingStatus = NYListingStatus;
		}
	}
	
	// Getters for the fields of the Species class
	
	/**
	 * Returns the category name of this species.
	 * @return	the category name of the species.
	 */
	public String getCategory() { return this.category; }
	
	/**
	 * Returns the taxonomic group of this species.
	 * @return	the taxonomic group name of the species.
	 */
	public String getTaxonomicGroup() { return this.taxonomicGroup; }
	
	/**
	 * Returns the taxonomic sub group of this species
	 * @return	the taxonomic sub group of the species
	 */
	public String getTaxonomicSubGroup() { return this.taxonomicSubGroup; }
	
	/**
	 * Returns the scientific name of this species
	 * @return	the scientific name of the species
	 */
	public String getScientificName() { return this.scientificName; }
	
	/**
	 * Returns the common name of this species
	 * @return	the common name of the species
	 */
	public String getCommonName() { return this.commonName; }
	
	/**
	 * Returns the NY Listing Status of this species
	 * @return	the NY Listing Status of the species
	 */
	public String getNYListingStatus() { return this.NYListingStatus; }

	/**
	 * Compares this object with the specified object for order. Implemented from the Comparable<> interface.
	 * Species objects are compared by common name first, and then if equal, compared by scientific name.
	 * @param o	the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, 
	 * or greater than the specified object.
	 */
	@Override
	public int compareTo(Species o) {
		int a = this.getCommonName().compareToIgnoreCase(o.getCommonName());	
		if (a == 0)
			a = this.getScientificName().compareToIgnoreCase(o.getScientificName());
		
		return a;
	}
	
	/**
	 * Returns the String implementation of this species
	 * @return	the String implementation of this species object.
	 */
	public String toString() {
		return String.format(
		"%s (%s)\n%s, %s\n%s\nPresent in %d / 62 counties", 
		getCommonName(), getScientificName(), getTaxonomicGroup(), getTaxonomicSubGroup(), getNYListingStatus(),
		counties.size());
	}
	
	/**
	 * Indicates whether some object obj is "equal to" this one. 
	 * Species objects are considered equal if have the same values for the common name, scientific name, 
	 * taxonomic group, taxonomic subgroup and category.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Species))
			return false;
		
		Species o = (Species) obj;
		if (!this.getCommonName().equalsIgnoreCase(o.getCommonName()))
			return false;
		
		if (!this.getTaxonomicGroup().equalsIgnoreCase(o.getTaxonomicGroup()))
			return false;
		
		if (!this.getScientificName().equalsIgnoreCase(o.getScientificName()))
			return false;
		
		if (!this.getTaxonomicSubGroup().equalsIgnoreCase(o.getTaxonomicSubGroup()))
			return false;
		
		if (!this.getCategory().equalsIgnoreCase(o.getCategory()))
			return false;	
		
		return true;
	}

	/**
	 * Returns true or false to indicate if the species is present in the specified county.
	 * @param county	input county
	 * @return	true if the species is present in the input county; false otherwise.
	 * @throws IllegalArgumentException	if the input county is null or empty.
	 */
	protected boolean isPresentIn(String county) throws IllegalArgumentException {
		if (county == null || county.equals("")) {
			throw new IllegalArgumentException("Null/empty strings are not allowed");
		}	
		for (String s : counties) {
			if (s.equals(county))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Adds this county to the list of counties in which the species is present.
	 * @param county	input county
	 * @return	true if the species was not already listed in this county; false if the county was already
	 * 			listed in this species' list of counties.
	 * @throws IllegalArgumentException
	 */
	protected boolean addCounty(String county) throws IllegalArgumentException {
		if (county == null || county.equals("")) {
			throw new IllegalArgumentException("Null/empty strings are not allowed");
		}
		if (this.isPresentIn(county))
			return false;
		
		counties.add(county);
		return true;
	}
	
	/**
	 * Returns the list of counties for this Species object.
	 * @return	ArrayList<String> representation list of counties.
	 */
	public ArrayList<String> getCounties() {
		return counties;
	}

}