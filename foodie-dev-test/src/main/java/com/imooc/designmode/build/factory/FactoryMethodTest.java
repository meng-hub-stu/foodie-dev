package com.imooc.designmode.build.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 不同的类都实现工厂类的接口，工厂类中一个创建类的方法，什么类实现就创建什么类，类都实现一个产品的行为，返回行为接口引用就可以了
 *  创建不同的类都返回行为接口的引用，
 *  行为接口有一个方法，需要实例类进行实现
 *  创建类时使用工厂类的接口进行创建，通过读取xml的配置，同各国反射进行将类实例化
 * @author Mengdl
 * @date 2022/04/19
 */
public class FactoryMethodTest {
    public static void main(String[] args) throws SAXException, IllegalAccessException, IOException, InstantiationException, ParserConfigurationException, ClassNotFoundException {
        LogFactory logFactory = (LogFactory) XmlUtils();
        Log log = logFactory.createLog();
        log.writeLog();

    }
    //该方法用于从XML配置中提取出具体的类名,并且返回一个实例对象
    public static Object XmlUtils() throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //创建DOM文件对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document =  documentBuilder.parse("src/main/resources/config/config.xml");

        //获取包含类名的文件节点
        NodeList nodeList = document.getElementsByTagName("className");
        Node classNode = nodeList.item(0).getFirstChild();
        String valueClass = classNode.getNodeValue();

        //通过类名生成实例对象,并且返回
        Class className = Class.forName(valueClass);
        Object o = className.newInstance();
        return o;
    }

}
