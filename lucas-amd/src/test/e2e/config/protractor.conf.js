exports.config = {
    
    directConnect: true,
    seleniumServerJar:'${selenium-standalone-jar.location}',

    // Capabilities to be passed to the webdriver instance.
    capabilities: {
        browserName: 'firefox'
    },

    //set baseUrl so we don't have to do this in each spec file
    baseUrl:'http://localhost:9020/amd/app/index.html#/',

    // Spec patterns are relative to the location of the spec file. They may
    // include glob patterns.
    specs: ['../../e2e/specs/*.js'],

    //make the browser maximized so that it suits for all kinds of devices; some tests are failing when run in lower resolutions.
    //however this issue is only in firefox.
    onPrepare : function() {
        browser.driver.manage().window().maximize();
    },

    // Options to be passed to Jasmine-node.
    jasmineNodeOpts: {
        showColors: true,             // Use colors in the command line report.
        isVerbose: true,              //display spec names as they are being executed
        includeStackTrace: true,      //print stacktrace when things go wrong
        defaultTimeoutInterval: 15000 //set 15 second default timeout
    }
};