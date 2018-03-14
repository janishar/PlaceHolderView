const React = require('react');

class Footer extends React.Component {
  docUrl(doc, language) {
    const baseUrl = this.props.config.baseUrl;
    return baseUrl + 'docs/' + (language ? language + '/' : '') + doc;
  }

  pageUrl(doc, language) {
    const baseUrl = this.props.config.baseUrl;
    return baseUrl + (language ? language + '/' : '') + doc;
  }

  render() {
    const currentYear = new Date().getFullYear();
    return (
      <footer className="nav-footer" id="footer">
        <section className="sitemap">
          <a href={this.props.config.baseUrl} className="nav-home">
            {this.props.config.footerIcon && (
              <img
                src={this.props.config.baseUrl + this.props.config.footerIcon}
                alt={this.props.config.title}
                width="66"
                height="58"
              />
            )}
          </a>
          <div>
            <h5>Sections</h5>
            <a href={this.docUrl('introduction.html')}>
              Getting Started
            </a>
            <a href={this.docUrl('terminology.html')}>
              PlaceHolderView
            </a>
            <a href={this.docUrl('iphv-intro.html')}>
              InfinitePlaceHolderView
            </a>
            <a href={this.docUrl('ephv-intro.html')}>
              ExpandablePlaceHolderView
            </a>
            <a href={this.docUrl('sphv-intro.html')}>
              SwipePlaceHolderView
            </a>
            <a href={this.docUrl('sdv-intro.html')}>
              SwipeDirectionalView
            </a>
            {/* <a href={this.docUrl('doc2.html', this.props.language)}>
              Compiler
            </a>
            <a href={this.docUrl('doc2.html', this.props.language)}>
              Contributing
            </a> */}
          </div>
          <div>
            <h5>Connect with me</h5>
            <a
              href="https://twitter.com/janisharali"
              target="_blank">
              Twitter
            </a>
            <a
              href="https://github.com/janishar"
              target="_blank">
              GitHub
            </a>
            <a
              href="https://www.linkedin.com/in/janishar-ali-8135a451/"
              target="_blank">
              LinkedIn
            </a>
            <a
              href="https://www.facebook.com/janishar.ali"
              target="_blank">
              Facebook
            </a>
            <a
              href="https://plus.google.com/+JanisharAli"
              target="_blank">
              Google Plus
            </a>
          </div>
          <div>
            <h5>More</h5>
            <a href={this.props.config.baseUrl + 'blog'}>Examples</a>
            <a href="https://github.com/janishar/PlaceHolderView">GitHub Repo</a>
            <a
              className="github-button"
              href={this.props.config.repoUrl}
              data-icon="octicon-star"
              data-count-href="/facebook/docusaurus/stargazers"
              data-show-count={true}
              data-count-aria-label="# stargazers on GitHub"
              aria-label="Star this project on GitHub">
              Star
            </a>
            <a href="https://mindorks.com/join-community">Join Community</a>
          </div>
        </section>

        <section className="copyright">
          Copyright &copy; 2016 Janishar Ali Anwar.
        </section>
      </footer>
    );
  }
}

module.exports = Footer;
