package converter;

import java.util.HashMap;
import java.util.List;

import tree.GenericTree;
import tree.GenericTreeNode;

public class Converter {

	public void parseFile(String path) {
		// given a path, parse a properties file in the path.
	}

	/*
	 * Parse a document by splitting by newline characters. Trees are created if
	 * root value doesn't exist previously. Otherwise trees are traversed and new
	 * children added if necessary.
	 */
	public HashMap<String, GenericTree<Prop>> parseString(String document) {

		HashMap<String, GenericTree<Prop>> parsedTrees = new HashMap<>();

		String[] lines = document.split("\n");

		for (int j = 0; j < lines.length; j++) {

			String[] valueSplit = lines[j].split("=");
			String properties = valueSplit[0];
			String finalValue = valueSplit[1].trim();
			String[] propertiesSplit = properties.split("\\."); // need the backslashes since regex "." means any char

			GenericTreeNode<Prop> rootNode = new GenericTreeNode<Prop>();
			GenericTree<Prop> tree = new GenericTree<Prop>();

			String rootName = propertiesSplit[0].trim();

			if (parsedTrees.containsKey(rootName)) {
				rootNode = parsedTrees.get(rootName).getRoot();
			}

			else {
				Prop rootProp = new Prop();
				rootProp.setName(rootName);
				rootNode.setData(rootProp);
				tree.setRoot(rootNode);
				parsedTrees.put(rootName, tree);
			}

			GenericTreeNode<Prop> currentNode = rootNode;

			for (int i = 1; i < propertiesSplit.length; i++) {

				String currentlyReadProperty = propertiesSplit[i].trim();
				GenericTreeNode<Prop> existingChildNode = hasExistingNodeWithSameName(currentNode.getChildren(),
						currentlyReadProperty);

				if (existingChildNode == null) {
					Prop currentProp = new Prop();
					currentProp.setName(currentlyReadProperty);
					if (i == propertiesSplit.length - 1) {
						currentProp.setValue(finalValue);
					}
					GenericTreeNode<Prop> newChildNode = new GenericTreeNode<Prop>(currentProp);
					currentNode.addChild(newChildNode);
					for (GenericTreeNode<Prop> p : currentNode.getChildren()) {
					}
					currentNode = newChildNode;
					
				}
				
				else currentNode = existingChildNode;
				if (i == propertiesSplit.length -1) {
					currentNode.getData().setValue(finalValue);
				}
			}
		}
		
		return parsedTrees;
	}
	
	
	public void printXml(GenericTree<Prop> tree) {
		// given a tree, generate an xml file
		return;
	}

	/*
	 * Given a list of nodes, find if a node with the same name exists. If it does,
	 * return the unique node. Otherwise, return null.
	 */
	public GenericTreeNode<Prop> hasExistingNodeWithSameName(List<GenericTreeNode<Prop>> nodeList, String name) {
		for (GenericTreeNode<Prop> node : nodeList) {
			if (node.getData().getName().equals(name))
				return node;
		}

		return null;
	}

}
