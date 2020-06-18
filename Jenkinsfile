#!groovy

node {
  try {
    stage('Checkout') {
      checkout scm
    }

    stage('BuildWithJava7AndUpload') {       
      docker.build("registry.nspop.dk/components/openxds:${DOCKERTAG}",  '--pull .')
    }

  } catch (err) {
    //slackSend channel: '<channelname>', color: 'bad', message: "${env.JOB_NAME} ${env.BUILD_NUMBER} - Build failed ... (<${env.BUILD_URL}|Open>)", tokenCredentialId: 'Slack-Token'
    throw err
  } finally {
    stage ('Clean') {
      deleteDir()
    }
  }
}
