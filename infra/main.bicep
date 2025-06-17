@description('Project suffix')
param projectSuffix string = 'solid-linkedin'

@description('Image name and version')
param imageName string = 'pioptak/app-solid'

@description('Resource group location')
param location string = resourceGroup().location

var sku = 'F1'
var appServicePlanName = 'app-plan-${projectSuffix}'
var webSiteName = 'app-${projectSuffix}'
var linuxFxVersion = 'DOCKER|${imageName}'

resource appServicePlan 'Microsoft.Web/serverfarms@2023-12-01' = {
  name: appServicePlanName
  location: location
  properties: {
    reserved: true
  }
  sku: {
    name: sku
  }
  kind: 'linux'
}

resource appService 'Microsoft.Web/sites@2023-12-01' = {
  name: webSiteName
  location: location
  properties: {
    serverFarmId: appServicePlan.id
    siteConfig: {
      linuxFxVersion: linuxFxVersion
    }
  }
}