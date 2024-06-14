/* global mermaid */

function mermaidReady() {
	// The Mermaid initialization code renders the Mermaid code within any element with class "mermaid" or
	// ID "mermaid". However in some cases some elements might have this ID but not be Mermaid code.
	// For example, Markdown code like this:
	//
	//     # Mermaid
	//
	// Will generate this HTML:
	//
	//     <h1 id="mermaid">Mermaid</h1>
	//
	// And that's going to make the lib set the `mermaid` object to the H1 element.
	// So below, we double-check that what we have really is an instance of the library.
	return typeof mermaid !== 'undefined' && mermaid !== null && typeof mermaid === 'object' && !!mermaid.initialize;
}

const isDarkMode = () => {
	// If any mermaid elements are marked as requiring dark mode, render *all*
	// mermaid elements in dark mode.
	return !!document.querySelector('.mermaid.joplin--mermaid-use-dark-theme');
};

function mermaidInit() {
	if (mermaidReady()) {
		const mermaidTargetNodes = document.getElementsByClassName('mermaid');

		try {
			const darkMode = isDarkMode();
			mermaid.initialize({
				// We call mermaid.run ourselves whenever the note updates. Don't auto-start
				startOnLoad: false,

				darkMode,
				theme: darkMode ? 'dark' : 'default',
			});
			mermaid.run({
				nodes: mermaidTargetNodes,
			});
		} catch (error) {
			console.error('Mermaid error', error);
		}

		// Resetting elements size - see mermaid.ts
		for (const element of mermaidTargetNodes) {
			element.style.width = '100%';
		}
	}
}

document.addEventListener('joplin-noteDidUpdate', () => {
	mermaidInit();
});

const initIID_ = setInterval(() => {
	const isReady = mermaidReady();
	if (isReady) {
		clearInterval(initIID_);
		mermaidInit();
	}
}, 100);

document.addEventListener('DOMContentLoaded', () => {
	// In some environments, we can load Mermaid immediately (e.g. mobile).
	// If we don't load it immediately in these environments, Mermaid seems
	// to initialize and auto-run, but without our configuration changes.
	if (mermaidReady()) {
		mermaidInit();
	} else {
		clearInterval(initIID_);
	}
});
