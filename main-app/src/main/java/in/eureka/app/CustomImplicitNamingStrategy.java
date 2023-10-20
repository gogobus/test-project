package in.eureka.app;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class CustomImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7694481919455205097L;

	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {

		final String name;

		if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
				|| source.getAttributePath() == null) {
			name = transformEntityName(source.getEntityNaming());// + '_' + source.getReferencedColumnName().getText();
		} else {
			name = transformAttributePath(source.getAttributePath());// + '_' +
																		// source.getReferencedColumnName().getText();
		}

		return toIdentifier(name, source.getBuildingContext());
	}
}
