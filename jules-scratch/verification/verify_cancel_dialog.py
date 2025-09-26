import asyncio
from playwright.sync_api import sync_playwright, expect

def verify_fix_with_js_click():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page()

        # Using port 8001 where the server is running
        page.goto('http://localhost:8001/frontend/')

        # Wait for the page to be somewhat ready
        page.wait_for_load_state('domcontentloaded')

        # --- Setup: Manually make both dialogs visible ---
        page.evaluate("""
            document.getElementById('type-dialog').style.display = 'block';
            document.getElementById('details-dialog').style.display = 'block';
        """)

        # Verify they are now visible (as a sanity check)
        type_dialog = page.locator('#type-dialog')
        details_dialog = page.locator('#details-dialog')
        expect(type_dialog).to_be_visible()
        expect(details_dialog).to_be_visible()

        page.screenshot(path="jules-scratch/verification/before_js_click.png")

        # --- Act: Trigger the click using JavaScript's native click() ---
        page.evaluate("document.getElementById('cancel-details-dialog').click()")

        # --- Assert: Check the result ---
        # Add a small delay to allow the JS to execute
        page.wait_for_timeout(100)

        expect(details_dialog).to_be_hidden()
        expect(type_dialog).to_be_hidden()

        page.screenshot(path="jules-scratch/verification/after_js_click.png")

        browser.close()
        print("Verification script with JS click completed successfully.")

if __name__ == '__main__':
    verify_fix_with_js_click()