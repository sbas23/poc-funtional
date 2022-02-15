package com.poc.services.predicate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

import com.poc.config.LogFormatter;
import com.poc.models.Asset;
import com.poc.models.Asset.AssetType;

public class ActivosRefactor {
	
	public static final Logger log = Logger.getLogger(ActivosRefactor.class.getName());


	public static void main(String[] args) {
		
		log.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		Formatter formatter = new LogFormatter();
		handler.setFormatter(formatter);        
		log.addHandler(handler);
		
		final List<Asset> assets = Arrays.asList(
			new Asset(Asset.AssetType.BOND, 1000),
			new Asset(Asset.AssetType.BOND, 2000),
			new Asset(Asset.AssetType.STOCK, 3000),
			new Asset(Asset.AssetType.STOCK, 4000)
		);
		
		log.info(MessageFormat.format("todos los activos: {0}", totalAssetValues(assets, asset -> true)));
		log.info(MessageFormat.format("BOND: {0}", totalAssetValues(assets, asset -> asset.getType() == AssetType.BOND)));
		log.info(MessageFormat.format("BOND: {0}", totalAssetValues(assets, asset -> asset.getType() == AssetType.STOCK)));
		
	}
	
	private static int totalAssetValues(final List<Asset> assets,final Predicate<Asset> assetSelector) {
		return assets.stream()
				.filter(assetSelector)
				.mapToInt(Asset::getValue)
				.sum();
	}
}
