package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.DepositPageObject;
import pageObjects.HomePageObject;
import pageObjects.NewAccountPageObject;
import pageObjects.NewCustomerPageObject;
import pageObjects.PageFactoryManager;
import pageUIs.AbstractPageUI;
import pageUIs.DepositPageUI;
import pageUIs.HomePageUI;
import pageUIs.NewAccountPageUI;
import pageUIs.NewCustomerPageUI;

public class AbstractPage {
	int shortTimeout = 5;
	int longTimeout = 30;

	/* WEB BROWSER */
	public void openUrl(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void back(WebDriver driver) {
		driver.navigate().back();
	}

	public void forward(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public void sendKeyAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public String getTextAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	/* WEB ELEMENT */
	public void clickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void clickToElement(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void sendkeyToElement(WebDriver driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void sendkeyToElement(WebDriver driver, String value, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void selectItemInHtmlDropDown(WebDriver driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}

	public String getselectedItemInHtmlDropDown(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void selectItemInCustomDropDown(WebDriver driver, String scrollXpath, String parentXpath, String childXpath,
			String expectedItem) {
		JavascriptExecutor javaExcutor = (JavascriptExecutor) driver;
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		javaExcutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath(scrollXpath)));
		WebElement element = driver.findElement(By.xpath(parentXpath));
		// scroll
		javaExcutor.executeScript("arguments[0].scrollIntoView(true)", element);
		element.click();
		// get list
		List<WebElement> childList = driver.findElements(By.xpath(childXpath));
		// wait
		explicitWait.until(ExpectedConditions.visibilityOfAllElements(childList));
		// loop
		for (WebElement child : childList) {
			String textItem = child.getText().trim();
			System.out.println("Text in dropdown: " + textItem);
			if (textItem.equals(expectedItem)) {
				javaExcutor.executeScript("arguments[0].scrollIntoView()", child);
				child.click();
				break;
			}
		}
	}

	public String getAttributeInElement(WebDriver driver, String locator, String attributeName) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attributeName);
	}

	public String getTextInElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextInElementDynamic(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(WebDriver driver, String locator) {
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public void checkTheCheckbox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) 
		{
			element.click();
		}
	}

	public void unCheckTheCheckbox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) 
		{
			element.click();
		}
	}

	public boolean isControlDisplayed(WebDriver driver, String locator)
	{
		try
		{
			WebElement element = driver.findElement(By.xpath(locator));
			boolean status = element.isDisplayed();
			System.out.println(status);
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean isControlDisplayed(WebDriver driver, String locator, String... dynamicValue)
	{
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isDisplayed();
	}

	public boolean isControlUnDisplayed(WebDriver driver, String locator) {
		Date date = new Date();
		System.out.println("start time = " + date.toString());
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			date = new Date();
			System.out.println("End time = " + date.toString());
			overrideGlobalTimeout(driver, longTimeout);
			return false;
		} else {
			date = new Date();
			System.out.println("End time = " + date.toString());
			overrideGlobalTimeout(driver, longTimeout);
			return true;
		}
	}

	public boolean isControlUnDisplayed(WebDriver driver, String locator, String... dynamicValue) {
		Date date = new Date();
		System.out.println("start time = " + date.toString());
		overrideGlobalTimeout(driver, shortTimeout);
		locator = String.format(locator, (Object[]) dynamicValue);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			date = new Date();
			System.out.println("End time = " + date.toString());
			overrideGlobalTimeout(driver, longTimeout);
			return false;
		} else {
			date = new Date();
			System.out.println("End time = " + date.toString());
			overrideGlobalTimeout(driver, longTimeout);
			return true;
		}
	}

	public void overrideGlobalTimeout(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	public boolean isSelected(WebDriver driver, String locator)
	{
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isSelected();
	}

	public boolean isEnabled(WebDriver driver, String locator)
	{
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isEnabled();
	}

	/* WINDOWS */
	public void switchToWindowByTitle(WebDriver driver, String title)
	{
		Set<String> allWindow = driver.getWindowHandles();
		for (String runWindows : allWindow) {
			driver.switchTo().window(runWindows);
			String currentWindow = driver.getTitle().trim();
			if (currentWindow.equals(title)) {
				break;
			}
		}
	}

	public boolean closeAllWithoutParentWindows(WebDriver driver, String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	public void switchToIframe(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(locator);
	}

	public void switchToChildWindow(WebDriver driver, String parent) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parent)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	/* USER ACTIONS */
	public void doubleClickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.doubleClick(element).perform();
	}

	public void hoverToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	public void rightToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.contextClick(element).perform();
	}

	public void dragAndDrop(WebDriver driver, String locator1, String locator2) {
		WebElement element1 = driver.findElement(By.xpath(locator1));
		WebElement element2 = driver.findElement(By.xpath(locator2));
		Actions action = new Actions(driver);
		action.dragAndDrop(element1, element2).build().perform();
	}

	/* UPLOAD */
	public void uploadSingleFile(WebDriver driver, String locator, String folderName, String fileName) {
		String rootFolder = System.getProperty("user.dir");
		WebElement element = driver.findElement(By.xpath(locator));
		String filePath = rootFolder + "\\" + folderName + "\\" + fileName;
		element.sendKeys(filePath);
	}

	/* JAVASCRIPT EXECUTOR */
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		try {
			return javaExecutor.executeScript(javaScript);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		try {
			return javaExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public void highlightElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		javaExecutor.executeScript("arguments[0].style.border='6px groove red'", element);
	}

	public Object removeAttributeInDOM(WebDriver driver, String locator, String attribute) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(locator));
		try {
			return javaExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public void scrollToElement(WebDriver driver, String locator) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(locator));
		javaExecutor.executeScript("arguments[0].scrollIntoView(true);", locator);
	}

	public Object clickToElementByJS(WebDriver driver, String locator) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(locator));
		try {
			return javaExecutor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object clickToElementByJS(WebDriver driver, String locator, String... dynamicValue) {
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		locator = String.format(locator, (Object[]) dynamicValue);
		WebElement element = driver.findElement(By.xpath(locator));
		try {
			return javaExecutor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public boolean isImageDisplayed(WebDriver driver, String locator) {
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return (boolean) js.executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					element);
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

	/* WAIT */
	public void waitToElementVisible(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		try
		{
			waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void waitToElementVisible(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[]) dynamicValue);
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}

	public void waitToElementPresent(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.presenceOfElementLocated(byLocator));
	}

	public void waitToElementInvisible(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
	}

	public void waitForAlertPresence(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.alertIsPresent());
	}

	public void waitForControlClickable(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.elementToBeClickable(byLocator));
	}

	// dynamic Page
	public AbstractPage openDynamicPage(WebDriver driver, String pageName) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		switch (pageName) {
		case "New Customer":
			return PageFactoryManager.getNewCustomerPage(driver);
		case "New Account":
			return PageFactoryManager.getNewAccountPage(driver);
		case "Deposit":
			return PageFactoryManager.getDepositPage(driver);
		default:
			return PageFactoryManager.getHomePage(driver);
		}
	}

	// 100->1000 page
	public void openMoreDynamicPage(WebDriver driver, String pageName) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
	}

	public NewCustomerPageObject openNewCustomerPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.NEW_CUSTOMER_LINK);
		clickToElement(driver, AbstractPageUI.NEW_CUSTOMER_LINK);
		return PageFactoryManager.getNewCustomerPage(driver);

	}

	public NewAccountPageObject openNewAccountPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.NEW_ACCOUNT_LINK);
		clickToElement(driver, AbstractPageUI.NEW_ACCOUNT_LINK);
		return PageFactoryManager.getNewAccountPage(driver);
	}

	public DepositPageObject openDepositPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.DEPOSIT_LINK);
		clickToElement(driver, AbstractPageUI.DEPOSIT_LINK);
		return PageFactoryManager.getDepositPage(driver);
	}

	public HomePageObject openHomePage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.HOME_PAGE_LINK);
		clickToElement(driver, AbstractPageUI.HOME_PAGE_LINK);
		return PageFactoryManager.getHomePage(driver);
	}

	public void inputAccountNo(WebDriver driver, String accountNo) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "accountno");
		sendkeyToElement(driver, accountNo, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "accountno");
	}

	public void clickToSubmit(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ELEMENTS_BUTTON, "AccSubmit");
		clickToElement(driver, AbstractPageUI.DYNAMIC_ELEMENTS_BUTTON, "AccSubmit");
	}

	public void inputAmount(WebDriver driver, String amount) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "ammount");
		sendkeyToElement(driver, amount, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "ammount");
	}

	public void inputDescription(WebDriver driver, String description) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "desc");
		sendkeyToElement(driver, description, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "desc");
	}

	public void inputCutomerID(WebDriver driver, String customerID) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "cusid");
		sendkeyToElement(driver, customerID, AbstractPageUI.DYNAMIC_ELEMENTS_TEXTBOX, "cusid");
	}
}
