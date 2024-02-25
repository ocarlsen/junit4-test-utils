# Release Checklist

This document contains common steps when making a new release.


## Variables

Set the following in your shell for the scripts below

    version=1.0
    next_version=$(echo ${version} | awk -F. -v OFS=. '{$NF += 1 ; print}')
    echo ${version}
    echo ${next_version}


<br>

## Generate File

Run the following from your project directory to generate this checklist:

    project=$(basename $PWD)
    sed -e "s/\junit4-test-utils/${project}/" ~/Documents/Open\ Source/Release.md > Release.md
    mkdir -p src/main/audit
    mv Release.md src/main/audit/Release_${version}.md
    git add !$
    open !$

<br>

## Release

* [x] Make changes
* [x] Unit tests
* [x] Manual tests
* [x] Build and verify

        mvn clean verify
        
* [x] Check [code coverage](../../../target/site/jacoco/index.html) report
	* May be [aggregate](../../../report/target/site/jacoco-aggregate/index.html) instead

* [x] Update these for versions, match `develop` branch instead of `main`:
	* [x] README
   	* [x] POM
   	* [x] Example Code 
	* [x] .github/workflows/build.yml

* [ ] Commit and push

        git add -u
        git commit -m "Preparing for ${version} release"
        git push  # develop

* [x] Verify in GitHub: [develop](https://github.com/ocarlsen/${project}/tree/develop) branch

* [x] Confirm build running on GitHub [Actions](https://github.com/ocarlsen/${project}/actions)

* [x] Check develop metrics in [Sonar Cloud](https://sonarcloud.io/dashboard?branch=develop&id=ocarlsen_${project})


* [x] Deploy snapshot to OSSRH

        mvn clean deploy
	
* [x] Confirm in [Staging](https://s01.oss.sonatype.org/content/groups/staging/com/ocarlsen/) repo
	* https://s01.oss.sonatype.org/content/groups/staging/com/ocarlsen/test/junit4-test-utils/1.0-SNAPSHOT/

* [x] Confirm in [Snapshots](https://s01.oss.sonatype.org/content/repositories/snapshots/com/ocarlsen/) repo
	* https://s01.oss.sonatype.org/content/repositories/snapshots/com/ocarlsen/test/junit4-test-utils/1.0-SNAPSHOT/

* [x] Test in another project, e.g. [open-source-tester](https://github.com/ocarlsen/open-source-tester)
    * https://github.com/ocarlsen/open-source-tester/tree/develop/junit4-test-utils/1.0-SNAPSHOT

* [x] Make a dry-run release

        mvn release:prepare \
            -DreleaseVersion=${version} \
            -DdevelopmentVersion=${next_version}-SNAPSHOT \
            -Dtag="v${version}" \
            -DdryRun=true

* [x] Fix any problems.  (You may need to update XCode too.)

* [x] Prepare the release for real

        mvn release:clean    # Clean up from dry run
        mvn release:prepare \
            -DreleaseVersion=${version} \
            -DdevelopmentVersion=${next_version}-SNAPSHOT \
            -Dtag="v${version}" 

* [x] Confirm and clean up

        git tag  # List tags
        mvn release:clean

* [x] Set some variables

        # We'll use these below
        tag=$(git tag | tail -1)
        echo $tag

* [x] Check out main

        git co main

* [x] Merge from release commit without committing:

        git merge "v${version}" --no-commit
        
    You should see a message like this:
    
        Automatic merge went well; stopped before committing as requested
        
* [x] Resolve conflicts, if any:

        git mergetool
        # Do not Release.md! git clean -f

* [x] Build to get latest README on `main`

        mvn clean verify

* [x] Confirm versions, and use `main` branch instead of `develop`:
    * [x] `pom.xml`
    * [x] `src/main/doc/README.md`
    * [x] .github/workflows/build.yml

* [x] Confirm versions match:

        pom_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
        readme_version=$(grep '<version>.*</version>' README.md | sed -e 's/<version>//' -e 's/<\/version>//' | tr -d '[:space:]')
        if [[ "$pom_version" != "$readme_version" ]]; then
            echo "Versions don't match: $pom_version != $readme_version"
        else
            echo "Versions match"
        fi

* [x] Commit and push when ready:
    
        git add -u
        git difftool --cached
        git commit
        git push # main
        git push origin ${tag}
        
* [x] Confirm changes on GitHub [main](https://github.com/ocarlsen/${project}/tree/main)
    * https://github.com/ocarlsen/junit4-test-utils/tree/main
        
* [x] Confirm build running on GitHub [Actions](https://github.com/ocarlsen/${project}/actions)
    * https://github.com/ocarlsen/junit4-test-utils/actions

* [x] Check [SonarCloud](https://sonarcloud.io/dashboard?id=ocarlsen_${project}) for quality metrics
    * https://sonarcloud.io/project/overview?id=ocarlsen_junit4-test-utils

* [x] Go go GitHub [Tags](https://github.com/ocarlsen/${project}/tags) and make release from new tag
    * https://github.com/ocarlsen/junit4-test-utils/tags

    * [x] Use tag as release title
    * [ ] Use markdown with format like this

            # Release Notes
    
            ## Enhancements
            
            * TODO
    
            ## Issues Fixed
    
            * TODO

            ## Dependencies
            
            ### Compile
            
            ### Test

    * [x] Generate list of dependencies for Release Notes

            mvn dependency:list | grep ":.*:.*:.*" | grep -v "Finished at" | sed 's/^\[INFO\] *//' | sort -k 5 -k 1 -t ':'

    * [x] Add to Release Notes with separate sections for compile, test, etc. with formatting.

* [x] Confirm on [Releases](https://github.com/ocarlsen/${project}/releases) page
    * https://github.com/ocarlsen/junit4-test-utils/releases/tag/v1.0

* [ ] Build and deploy artifacts to OSSRH

        mvn clean deploy -P release

      You may have to set this variable if you see (`gpg: signing failed: Inappropriate ioctl for device`): 
      
        GPG_TTY=$(tty)
        export GPG_TTY 
      
      You may also have to enter the password to sign the JAR.

* [x] Confirm in [Staging](https://s01.oss.sonatype.org/content/groups/staging/com/ocarlsen/) repo
	* https://s01.oss.sonatype.org/content/groups/staging/com/ocarlsen/test/junit4-test-utils/1.0/

* [x] Confirm in [Releases](https://s01.oss.sonatype.org/content/repositories/releases/com/ocarlsen/) repo
	* https://s01.oss.sonatype.org/content/repositories/releases/com/ocarlsen/test/junit4-test-utils/1.0/

* [x] Wait for [Maven Central](https://repo.maven.apache.org/maven2/com/ocarlsen/) to sync
	* https://repo.maven.apache.org/maven2/com/ocarlsen/test/junit4-test-utils/1.0/

## Site

* [x] Test building the site

        mvn clean site
    
    It will build to `target/site`:

        open target/site/index.html
    
* [x] Check no links are broken.  For example:

    * License
    * Project Summary, "Java Version" field

* [x] Build and publish site

        mvn clean site-deploy
        
    (Will push directly to `gh-pages` branch.)  

    For multi-module builds, you may have to follow steps in [Pages](/Users/ocarlsen/Documents/Open\ Source/Pages.md#multi-module) document.

* [x] Confirm site w/ latest version at [GitHub Pages](https://ocarlsen.github.io/${project}/)
    * https://ocarlsen.github.io/junit4-test-utils/


* [x] Commit this document to `src/main/audit`:

        git add src/main/audit
        git commit -m "Release checklist for ${version} release"

Yay!  You are done with the release.

<br>

## Develop

* [x] Check out `develop` to continue work:

        git co develop

* [x] Merge any changes from `main`:

        touch src/main/audit/Release_${version}.md
        git difftool main

* [x] Confirm versions match with:

        pom_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
        readme_version=$(grep '<version>.*</version>' README.md | sed -e 's/<version>//' -e 's/<\/version>//' | tr -d '[:space:]')
        if [[ "$pom_version" != "$readme_version" ]]; then
            echo "Versions don't match: $pom_version != $readme_version"
        else
            echo "Versions match"
        fi
        
* [x] Commit, push

        git add -u
        git commit -m "Updating develop to match main"
        git push # Includes Release changes from earlier

* [x] Compare this document with [original](/Users/ocarlsen/Documents/Open\ Source/Release.md):

        vdiff src/main/audit/Release_${version}.md ~/Documents/Open\ Source/Release.md

* [x] Commit this document to `src/main/audit`:

        git add src/main/audit
        git commit -m "Release checklist for ${version} release"

Yay!  Now you are done with this document.



***


