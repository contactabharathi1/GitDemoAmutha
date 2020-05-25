import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

currentDateAndTime = CustomKeywords.'wdx.p2.generateCurrentDateAndTime'().toString()

WebUI.comment('dev code')

todayDate = CustomKeywords.'wdx.p2.generateDateCustomFormat'(0, 'dd/MM/yyyy').toString()

prospectFirstName = 'PFirstName'

prospectLastName = GlobalVariable.activityLastName

prospectLastName = ((prospectLastName + ' ') + currentDateAndTime)

contactFullName = ((prospectFirstName + ' ') + prospectLastName)

opportunityName = 'oppurtnity'

opportunityName = ((opportunityName + ' ') + currentDateAndTime)

prospectFullName = ((prospectFirstName + ' ') + prospectLastName)

portfolioName = ('Portfolio ' + currentDateAndTime)

prospectCategoryType = 'Individual'

prospectSourceType = 'Spontaneous Contact'

not_run: prospectSourceType = 'Event'

not_run: CustomKeywords.'wdx.p2.loginToPortal2'(GlobalVariable.loginNameDev, GlobalVariable.loginPwdDev)

'Login to Private Banker'
CustomKeywords.'wdx.p2.loginToPortal2'(GlobalVariable.loginPage_name1, GlobalVariable.loginPage_password1)

WebUI.delay(10)

WebUI.delay(10)

'Navigating to Relationship Manager'
WebUI.callTestCase(findTestCase('CA/helpers/h_navigateToRelationshipManager'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(300)

not_run: WebUI.switchToFrame(findTestObject('P2Core/iFrames/Core/Dashboards/iframe_MainFrame_PrivateBanker'), 30)

WebUI.callTestCase(findTestCase('CA/helpers/page_WDXdashboard/h_selectTab'), [('tabName') : 'INDIVIDUALS (CLIENTS)'], FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'wdx.p2.clickText'(findTestObject('P2Core/XFrame_openedTab/xText_button'), 'All')

WebUI.setText(findTestObject('P2Core/XFrame_openedTab/search_field'), contactFullName)

WebUI.doubleClick(findTestObject('P2Core/XFrame_openedTab/search_icon'))

WebUI.verifyElementNotPresent(findTestObject('P2Core/General/loading_portaLoad_relationshipManager'), 30)

def contactAlreadyExist = CustomKeywords.'wdx.p2.waitForElementVisible'(findTestObject('P2Core/General/xRowName_yAnchorText'), 
    contactFullName, 5)

not_run: WebUI.delay(3)

if (!(contactAlreadyExist)) {
    WebUI.callTestCase(findTestCase('CA/helpers/page_WDXdashboard/h_selectTab'), [('tabName') : 'INDIVIDUALS (PROSPECTS)'], 
        FailureHandling.STOP_ON_FAILURE)

    System.out.println('SourceTypeis' + prospectSourceType)

    WebUI.callTestCase(findTestCase('CA/helpers/h_createProspect_list -Copy- Without Error Msg'), [('prospectFullName1') : prospectFullName
            , ('prospectFirstName1') : prospectFirstName, ('prospectLastName1') : prospectLastName, ('prospectCatType1') : prospectCategoryType
            , ('prospectSourceType1') : prospectSourceType], FailureHandling.STOP_ON_FAILURE)

    not_run: return prospectFullName
}

CustomKeywords.'wdx.p2.waitForElementVisible'(findTestObject('P2Core/General/PopUpWindow/xText_contains_anywhere'), prospectFirstName, 
    20)

