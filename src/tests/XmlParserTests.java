package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;

import converter.Converter;
import converter.Prop;
import converter.XMLReader;
import tree.GenericTree;
import tree.GenericTreeNode;

/*
Contains tests to ensure xml is parsed correctly into a tree. */

public class XmlParserTests {
	
	private Converter converter = new Converter();
	

	@Test
	public void xmlParserParsesSingleLineIntoTreeCorrectly() {
		String document = "hi.this.is.me = john";
		HashMap<String, GenericTree<Prop>> xmlTree = converter.parseString(document);
		GenericTree<Prop> tree = xmlTree.get("hi");
		assertEquals("hi", tree.getRoot().getData().getName());
		assertEquals("this", tree.getRoot().getChildren().get(0).getData().getName());
		assertEquals("is", tree.getRoot().getChildren().get(0).getChildren().get(0).getData().getName());
		assertEquals("john", tree.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(0).getData().getValue());
	}
	
	@Test
	public void xmlParseParsesMultilineIntoTreeCorrectly() {
		String s = "hey.this.is.me=yo \n hey.this.is.him=yoyo";
		GenericTree<Prop> xmlTree = converter.parseString(s).get("hey");
		List<GenericTreeNode<Prop>> childrenList = xmlTree.getRoot().getChildAt(0).getChildAt(0).getChildren();
		assertEquals(2, childrenList.size());
		assertEquals(5, xmlTree.getNumberOfNodes());
	}
	
	@Test
	public void printXmlOutputsExpectedDocument() {
		File xmlFile = new File("/home/pepejam/Code/generated.xml");
		NodeList nlist = XMLReader.parse(xmlFile);
		assertTrue(XMLReader.match("hello", nlist));
	}
}
