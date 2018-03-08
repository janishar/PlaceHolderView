const users = [
  // {
  //   caption: "User1",
  //   image: "/test-site/img/docusaurus.svg",
  //   infoLink: "https://www.example.com",
  //   pinned: true
  // }
];
const siteConfig = {
  title: 'PlaceHolderView' /* title for your website */,
  tagline: 'Advanced List and Stack Views',
  url: 'http://janishar.com' /* your website url */,
  baseUrl: '/PlaceHolderView/' /* base url for your project */,
  projectName: 'PlaceHolderView',
  headerLinks: [
    { doc: 'introduction', label: 'docs' },
    { blog: true, label: 'Examples' },
    { page: 'help', label: 'Help' },    
    { search: true },
  ],
  users,
  /* path to images for header/footer */
  headerIcon: 'img/logo.svg',
  footerIcon: 'img/logo.svg',
  favicon: 'img/favicon.png',
  /* colors for website */
  colors: {
    primaryColor: '#6200ea',
    secondaryColor: '#64dd17',
  },
  //search
  algolia: {
    apiKey: "my-search-only-api-key-1234",
    indexName: "my-index-name"
  },
  gaTrackingId: 'UA-115371900-1',
  facebookAppId: '100001054239147',
  twitter: 'true',
  /* custom fonts for website */
  /*fonts: {
    myFont: [
      "Times New Roman",
      "Serif"
    ],
    myOtherFont: [
      "-apple-system",
      "system-ui"
    ]
  },*/
  blogSidebarCount: 'ALL',
  // This copyright info is used in /core/Footer.js and blog rss/atom feeds.
  copyright:
    'Copyright © 2016 Janishar Ali Anwar',
  organizationName: 'janishar', // or set an env variable ORGANIZATION_NAME
  projectName: 'PlaceHolderView', // or set an env variable PROJECT_NAME

  highlight: {
    // Highlight.js theme to use for syntax highlighting in code blocks
    theme: 'routeros',
  },
  scripts: ['https://buttons.github.io/buttons.js'],
  // You may provide arbitrary config keys to be used as needed by your template.
  repoUrl: 'https://github.com/janishar/PlaceHolderView',
};

module.exports = siteConfig;